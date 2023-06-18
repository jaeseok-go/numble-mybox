package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.*;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import jaeseok.numble.mybox.common.util.ByteConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;

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

    public String login(LoginParam loginParam) {
        Member member = memberRepository.findByEmail(loginParam.getEmail())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        member.validatePassword(loginParam.getPassword());

        return jwtHandler.create(String.valueOf(member.getId()));
    }

    public MemberInfoResponse retrieveMember() {
        String id = jwtHandler.getId();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        Long bytes = member.calculateUsage();

        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .usage(UsageDto.builder()
                        .B(bytes)
                        .KB(ByteConvertor.toKiloBytes(bytes))
                        .MB(ByteConvertor.toMegaBytes(bytes))
                        .GB(ByteConvertor.toGigaBytes(bytes))
                        .build())
                .build();
    }
}
