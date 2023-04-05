package jaeseok.numble.mybox.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageHandler {
    String upload(MultipartFile file, String path) throws IOException;
}
