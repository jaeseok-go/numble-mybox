package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import jaeseok.numble.mybox.member.dto.mapper.MemberMapper;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    public Member signUp(MemberSignUpDto memberSignUpDto) {
        Member member = MemberMapper.INSTANCE.toMember(memberSignUpDto);
        return memberRepository.save(member);
    }
}
