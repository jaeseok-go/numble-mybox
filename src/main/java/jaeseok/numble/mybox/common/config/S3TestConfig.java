package jaeseok.numble.mybox.common.config;

import jaeseok.numble.mybox.storage.AmazonS3StorageHandler;
import jaeseok.numble.mybox.storage.StorageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("dev")
@Configuration
public class S3TestConfig {

    @Bean
    public StorageHandler storageHandler() {
        return new StorageHandler() {
            @Override
            public String upload(MultipartFile file, String path) throws IOException {
                return "";
            }
        };
    }
}
