package jaeseok.numble.mybox.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageHandler {
    String upload(MultipartFile file, String path) throws IOException;

    int deleteAll(List<Long> keys);

}
