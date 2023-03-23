package jaeseok.numble.mybox.common.auth;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleJwtHandlerTest {

    @Autowired
    private JwtHandler jwtHandler;

    @Test
    @DisplayName("Jwt 생성 : 성공")
    void testCreate() {
        // given
        String id = "test_id";

        // when
        String jwt = jwtHandler.create(id);

        // then
        Assertions.assertInstanceOf(String.class, jwt);
        Assertions.assertTrue(!jwt.isBlank());
    }

    @Test
    @DisplayName("Jwt getId : 성공")
    void testGetIdSuccess() {
        // given
        String inputId = "test_id";

        // when
        String jwt = jwtHandler.create(inputId);
        String outputId = jwtHandler.getId(jwt);

        // then
        Assertions.assertEquals(inputId, outputId);
    }

    @Test
    @DisplayName("Jwt getId : 실패")
    void testGetIdFail() {
        // given
        String jwt = "tr.ash_val.ue";

        // when
        Executable getId = () -> jwtHandler.getId(jwt);

        // then
        Assertions.assertThrows(ClassCastException.class, getId);
    }

    @Test
    @DisplayName("Jwt validate : 성공")
    void testValidateSuccess() {
        // given
        String id = "test_id";

        // when
        String jwt = jwtHandler.create(id);

        // then
        Assertions.assertTrue(jwtHandler.validate(jwt));
    }

    @Test
    @DisplayName("Jwt validate : 실패")
    void testValidateFail() {
        // given
        String jwt = "trash_value";

        // when
        Boolean validate = jwtHandler.validate(jwt);

        // then
        Assertions.assertFalse(validate);
    }
}