package jaeseok.numble.mybox.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public class AmazonS3StorageHandler implements StorageHandler{
    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public String upload(MultipartFile file, String name) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, name, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new MyBoxException(ResponseCode.FILE_UPLOAD_FAIL);
        }

        return "https://" + bucket + name;
    }
}
