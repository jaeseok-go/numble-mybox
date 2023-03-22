package jaeseok.numble.mybox.common.config;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.auth.SimpleJwtHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {
    @Bean
    public JwtHandler jwtHandler() {
        return new SimpleJwtHandler();
    }
}
