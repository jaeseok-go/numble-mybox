package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@DisplayName("MemberService Class")
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    private String id = "test_id";
    private String password = "1234";
    private String nickname = "테스트_닉네임";

    @Nested
    @DisplayName("회원 가입")
    class SignUp{
        @Test
        @DisplayName("성공")
        void success() {
            // given
            MemberSignUpDto memberSignUpDto = new MemberSignUpDto(id, password, nickname);

            // when
            String memberId = memberService.signUp(memberSignUpDto);

            // then
            Assertions.assertEquals(id, memberId);
        }

        @Test
        @DisplayName("실패 - 중복")
        void fail_to_duplicate() {
            // given
            MemberSignUpDto memberSignUpDto = new MemberSignUpDto(id, password, nickname);
            memberService.signUp(memberSignUpDto);

            // when
            Executable signUp = () -> memberService.signUp(memberSignUpDto);

            // then
            MyBoxException e = Assertions.assertThrows(MyBoxException.class, signUp);
            Assertions.assertEquals(e.getErrorCode(), "MBER0001");
        }
    }
}