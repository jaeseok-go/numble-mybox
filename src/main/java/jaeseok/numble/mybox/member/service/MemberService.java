package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.service.FolderService;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.*;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;
    private final FolderService folderService;

    @Transactional
    public SignUpResponse signUp(SignUpParam signUpParam) {
        String email = signUpParam.getEmail();

        checkDuplicate(email);

        Member joinMember = Member.builder()
                .email(email)
                .password(signUpParam.getPassword())
                .build();

        joinMember.createRootFolder();

        memberRepository.save(joinMember);

        return SignUpResponse.builder()
                .id(joinMember.getId())
                .email(email)
                .build();
    }

    private void checkDuplicate(String email) {
        if (exist(email)) {
            throw new MyBoxException(ResponseCode.MEMBER_EXIST);
        }
    }

    private boolean exist(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public LoginResponse login(LoginParam loginParam) {
        Member member = memberRepository.findByEmail(loginParam.getEmail())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        member.validatePassword(loginParam.getPassword());

        String jwt = jwtHandler.create(member.getId());
        Folder rootFolder = folderService.retrieveRootFolder(member);

        return new LoginResponse(jwt, rootFolder.getId(), retrieveMember());
    }

    public MemberInfoResponse retrieveMember() {
        Long id = jwtHandler.getId();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        Long byteUsage = member.calculateUsage();

        return new MemberInfoResponse(member, byteUsage);
    }
}
