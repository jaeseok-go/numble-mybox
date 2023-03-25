package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import jaeseok.numble.mybox.member.dto.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@ActiveProfiles("dev")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("MemberSignUpDto => Member 매핑 성공")
    void mappingDtoToMemberSuccess() {
        // given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("test_id", "1234", "테스트 닉네임");

        // when
        Member actual = MemberMapper.INSTANCE.toMember(memberSignUpDto);
        Member expect = new Member("test_id", "1234","테스트 닉네임", LocalDateTime.now());

        // then
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() {
        // given
        MemberSignUpDto memberSignUpDto = new MemberSignUpDto("test_id", "1234", "테스트 닉네임");

        // when
        Member actual = memberService.signUp(memberSignUpDto);
        Member expect = new Member("test_id", "1234","테스트 닉네임", LocalDateTime.now());

        // then
        Assertions.assertEquals(expect, actual);
    }
}