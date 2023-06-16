package jaeseok.numble.mybox.member.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.LoginParam;
import jaeseok.numble.mybox.member.dto.SignUpParam;
import jaeseok.numble.mybox.member.dto.SignUpResponse;
import jaeseok.numble.mybox.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@DisplayName("MemberService Class")
@WebMvcTest(MemberService.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private JwtHandler jwtHandler;

    private String email = "test_id";
    private String password = "1234";

    @Nested
    @DisplayName("회원 가입")
    class SignUp{
        @Test
        @DisplayName("성공")
        void success() {
            // given
            SignUpParam signUpParam = new SignUpParam(email, password);
            Member member = Member.builder()
                    .id(1L)
                    .email(email)
                    .password(password)
                    .build();

            BDDMockito.given(memberRepository.findByEmail(email)).willReturn(Optional.empty());
            BDDMockito.given(memberRepository.save(any())).willReturn(member);

            // when
            SignUpResponse response = memberService.signUp(signUpParam);

            // then
            Assertions.assertEquals(response.getEmail(), email);
        }

        @Test
        @DisplayName("이미 존재하는 회원 id가 있는 경우 실패")
        void duplicated_id_fail() {
            // given
            SignUpParam signUpParam = new SignUpParam(email, password);
            memberService.signUp(signUpParam);

            // when
            Executable signUp = () -> memberService.signUp(signUpParam);

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
            memberService.signUp(new SignUpParam(email, password));

            // when
            String jwt = memberService.login(new LoginParam(email, password));

            // then
            Assertions.assertTrue(jwt instanceof String);
            Assertions.assertTrue(jwt.contains("."));
        }

        @Test
        @DisplayName("존재하지 않는 id 입력 시 실패")
        void not_exist_id_fail() {
            // given
            memberService.signUp(new SignUpParam(email, password));

            // when
            Executable login = () -> memberService.login(new LoginParam("not_exist_id", password));

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
            memberService.signUp(new SignUpParam(email, password));

            // when
            Executable login = () -> memberService.login(new LoginParam(email, "invalid_password"));

            // then
            MyBoxException e = Assertions.assertThrows(MyBoxException.class, login);

            String invalidPasswordCode = ResponseCode.INVALID_PASSWORD.getCode();
            String errorCode = e.getErrorCode();
            Assertions.assertEquals(invalidPasswordCode, errorCode);
        }
    }
}