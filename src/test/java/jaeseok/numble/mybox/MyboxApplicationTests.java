package jaeseok.numble.mybox;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ContextConfiguration;

@EnableCaching
@ContextConfiguration(classes = MyboxApplication.class)
@SpringBootTest
class MyboxApplicationTests {

    @Test
    void contextLoads() {
    }

}
