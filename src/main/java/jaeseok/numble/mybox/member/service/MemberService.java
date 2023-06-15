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

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;
    private final FileService fileService;

    public SignUpResponse signUp(SignUpParam signUpParam) {
        Optional<Member> memberOptional = memberRepository.findByEmail(signUpParam.getEmail());

        if (memberOptional.isPresent()) {
            throw new MyBoxException(ResponseCode.MEMBER_EXIST);
        }

        Member member = memberRepository.save(Member.builder()
                .email(signUpParam.getEmail())
                .password(signUpParam.getPassword())
                .nickname(signUpParam.getNickname())
                .build());

        return new SignUpResponse(member.getId(), member.getEmail());
    }

    public String login(LoginDto loginDto) {
        Member member = memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        member.validatePassword(loginDto.getPassword());

        return jwtHandler.create(loginDto.getId());
    }

    public MemberInfoDto retrieveMember() {
        String id = jwtHandler.getId();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        Long usage = fileService.calculateUsageToByte(id);

        return MemberInfoDto.builder()
                .id(member.getEmail())
                .nickname(member.getNickname())
                .usage(UsageDto.builder()
                        .B(usage)
                        .KB(ByteConvertor.toKiloBytes(usage))
                        .MB(ByteConvertor.toMegaBytes(usage))
                        .GB(ByteConvertor.toGigaBytes(usage))
                        .build())
                .build();
    }
}
