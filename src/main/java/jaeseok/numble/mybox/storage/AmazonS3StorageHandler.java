package jaeseok.numble.mybox.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class AmazonS3StorageHandler implements StorageHandler {
    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public String upload(MultipartFile file, FileKey fileKey) throws IOException{
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileKey.getKey(), file.getInputStream(), metadata);

        return "https://" + bucket + fileKey.getKey();
    }

    @Override
    public InputStream download(FileKey fileKey) {
        return amazonS3Client
                .getObject(new GetObjectRequest(bucket, fileKey.getKey()))
                .getObjectContent();
    }

    @Override
    public int delete(FileKey fileKey) {
        DeleteObjectRequest objectDeleteRequest =
                new DeleteObjectRequest(bucket, fileKey.getKey());
        amazonS3Client.deleteObject(objectDeleteRequest);

        return 1;
    }
}
