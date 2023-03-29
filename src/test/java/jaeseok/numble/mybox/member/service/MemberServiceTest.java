package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.dto.LoginDto;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        @DisplayName("이미 존재하는 회원 id가 있는 경우 실패")
        void duplicated_id_fail() {
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

    @Nested
    @DisplayName("로그인")
    class Login {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            memberService.signUp(new MemberSignUpDto(id, password, nickname));

            // when
            String jwt = memberService.login(new LoginDto(id, password));

            // then
            Assertions.assertTrue(jwt instanceof String);
            Assertions.assertTrue(jwt.contains("."));
        }

        @Test
        @DisplayName("존재하지 않는 id 입력 시 실패")
        void not_exist_id_fail() {
            // given
            memberService.signUp(new MemberSignUpDto(id, password, nickname));

            // when
            Executable login = () -> memberService.login(new LoginDto("not_exist_id", password));

            // then
            MyBoxException e = Assertions.assertThrows(MyBoxException.class, login);

            String memberNotFoundCode = ResponseCode.MEMBER_NOT_FOUND.getCode();
            String errorCode = e.getErrorCode();
            Assertions.assertEquals(memberNotFoundCode, errorCode);
        }

        @Test
        @DisplayName("유효하지 않은 password 입력 시 실패")
        void invalid_password_fail() {
            // given
            memberService.signUp(new MemberSignUpDto(id, password, nickname));

            // when
            Executable login = () -> memberService.login(new LoginDto(id, "invalid_password"));

            // then
            MyBoxException e = Assertions.assertThrows(MyBoxException.class, login);

            String invalidPasswordCode = ResponseCode.INVALID_PASSWORD.getCode();
            String errorCode = e.getErrorCode();
            Assertions.assertEquals(invalidPasswordCode, errorCode);
        }
    }
}