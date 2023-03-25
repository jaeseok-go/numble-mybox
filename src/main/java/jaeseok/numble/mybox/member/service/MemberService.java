package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.LoginDto;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import jaeseok.numble.mybox.member.dto.UsageDto;
import jaeseok.numble.mybox.member.dto.MemberInfoDto;
import jaeseok.numble.mybox.member.dto.mapper.MemberMapper;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import jaeseok.numble.mybox.common.util.ByteConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;
    private final FileService fileService;
    public Member signUp(MemberSignUpDto memberSignUpDto) {
        Member member = MemberMapper.INSTANCE.toMember(memberSignUpDto);
        return memberRepository.save(member);
    }

    public String login(LoginDto loginDto) {
        memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        return jwtHandler.create(loginDto.getId());
    }

    public MemberInfoDto retrieveMember() {
        String id = jwtHandler.getId();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        Long usage = fileService.calculateUsageToByte(id);

        return MemberInfoDto.builder()
                .id(member.getId())
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
