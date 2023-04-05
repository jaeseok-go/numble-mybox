package jaeseok.numble.mybox.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public class AmazonS3StorageHandler implements StorageHandler {
    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public String upload(MultipartFile file, String path) throws IOException{
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, path, file.getInputStream(), metadata);

        return "https://" + bucket + path;
    }
}
