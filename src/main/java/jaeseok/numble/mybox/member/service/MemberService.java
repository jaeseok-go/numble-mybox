package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.LoginDto;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import jaeseok.numble.mybox.member.dto.mapper.MemberMapper;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtHandler jwtHandler;

    public Member signUp(MemberSignUpDto memberSignUpDto) {
        Member member = MemberMapper.INSTANCE.toMember(memberSignUpDto);
        return memberRepository.save(member);
    }
    public String login(LoginDto loginDto) {
        Member member = memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.MEMBER_NOT_FOUND));

        return jwtHandler.create(loginDto.getId());
    }
}
