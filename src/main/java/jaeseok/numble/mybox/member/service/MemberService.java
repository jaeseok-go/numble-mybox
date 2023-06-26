package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.*;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import jaeseok.numble.mybox.common.util.ByteConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;
    private final FileService fileService;

    @Transactional
    public SignUpResponse signUp(SignUpParam signUpParam) {
        Optional<Member> memberOptional = memberRepository.findByEmail(signUpParam.getEmail());

        if (memberOptional.isPresent()) {
            throw new MyBoxException(ResponseCode.MEMBER_EXIST);
        }

        Member member = memberRepository.save(Member.builder()
                .email(signUpParam.getEmail())
                .password(signUpParam.getPassword())
                .build());

        member.createRootFolder();

        return new SignUpResponse(member.getId(), member.getEmail());
    }

    public LoginResponse login(LoginParam loginParam) {
        Member member = memberRepository.findByEmail(loginParam.getEmail())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        member.validatePassword(loginParam.getPassword());

        String jwt = jwtHandler.create(member.getId());
        Long rootFolderId = fileService.retrieveRootFolder().getId();

        return new LoginResponse(jwt, rootFolderId);
    }

    public MemberInfoResponse retrieveMember() {
        Long id = jwtHandler.getId();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        Long byteUsage = member.calculateUsage();

        return new MemberInfoResponse(member, byteUsage);
    }
}
