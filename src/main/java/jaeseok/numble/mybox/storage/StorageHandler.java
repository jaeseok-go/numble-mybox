package jaeseok.numble.mybox.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface StorageHandler {
    String upload(MultipartFile file, FileKey fileKey) throws IOException;

    InputStream download(FileKey fileKey);

    int delete(FileKey fileKey);
}
