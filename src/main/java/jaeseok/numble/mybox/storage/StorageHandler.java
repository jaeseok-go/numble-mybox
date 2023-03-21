package jaeseok.numble.mybox.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageHandler {
    String upload(MultipartFile file, String name);
}
