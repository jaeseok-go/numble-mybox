package jaeseok.numble.mybox.storage.config;

import jaeseok.numble.mybox.storage.FileKey;
import jaeseok.numble.mybox.storage.StorageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Profile("dev")
@Configuration
public class DevS3Config {

    @Bean
    public StorageHandler storageHandler() {
        return new StorageHandler() {

            @Override
            public String upload(MultipartFile file, FileKey fileKey) throws IOException {
                return null;
            }

            @Override
            public int deleteAll(List<FileKey> fileKeys) {
                return 0;
            }
        };
    }
}
