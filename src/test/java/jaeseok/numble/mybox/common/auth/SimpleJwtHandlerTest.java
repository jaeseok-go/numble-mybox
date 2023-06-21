package jaeseok.numble.mybox.common.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@DisplayName("SimpleJwtHandler Class")
@WebMvcTest(SimpleJwtHandler.class)
class SimpleJwtHandlerTest {

    @Autowired
    private SimpleJwtHandler jwtHandler;

    @Nested
    @DisplayName("jwt 생성")
    class Create {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long id = 1L;

            // when
            String jwt = jwtHandler.create(id);

            // then
            Assertions.assertInstanceOf(String.class, jwt);
            Assertions.assertTrue(!jwt.isBlank());
        }
    }

    @Nested
    @DisplayName("jwt에서 회원id를 획득")
    class GetId {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long inputId = 1L;

            // when
            String jwt = jwtHandler.create(inputId);
            Long outputId = jwtHandler.getId(jwt);

            // then
            Assertions.assertEquals(inputId, outputId);
        }

        @Test
        @DisplayName("유효하지 않은 jwt를 입력받는 경우 실패")
        void fail() {
            // given
            String jwt = "tr.ash_val.ue";

            // when
            Executable getId = () -> jwtHandler.getId(jwt);

            // then
            Assertions.assertThrows(ClassCastException.class, getId);
        }
    }

    @Nested
    @DisplayName("jwt 유효성 검증")
    class Validate {
        @Test
        @DisplayName("성공")
        void success() {
            // given
            Long id = 1L;

            // when
            String jwt = jwtHandler.create(id);

            // then
            Assertions.assertTrue(jwtHandler.validate(jwt));
        }

        @Test
        @DisplayName("유효하지 않은 jwt를 입력받는 경우 실패")
        void fail() {
            // given
            String jwt = "tra.sh_val.ue";

            // when
            Boolean validate = jwtHandler.validate(jwt);

            // then
            Assertions.assertFalse(validate);
        }
    }
}