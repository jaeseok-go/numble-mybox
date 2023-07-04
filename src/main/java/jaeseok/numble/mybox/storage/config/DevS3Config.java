package jaeseok.numble.mybox.storage.config;

import jaeseok.numble.mybox.storage.FileKey;
import jaeseok.numble.mybox.storage.StorageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class DevS3Config {

    @Bean
    public StorageHandler devStorageHandler() {
        return new StorageHandler() {

            @Override
            public String upload(MultipartFile file, FileKey fileKey) throws IOException {
                return null;
            }

            @Override
            public InputStream download(FileKey fileKey) {
                return null;
            }

            @Override
            public int delete(FileKey fileKey) {
                return 0;
            }
        };
    }
}
