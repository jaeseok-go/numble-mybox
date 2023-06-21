package jaeseok.numble.mybox.storage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public int deleteAll(List<FileKey> fileKeys) {
        List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        for (FileKey fileKey : fileKeys) {
            keys.add(new DeleteObjectsRequest.KeyVersion(fileKey.getKey()));
        }

        DeleteObjectsRequest multiObjectDeleteRequest =
                new DeleteObjectsRequest(bucket)
                .withKeys(keys)
                .withQuiet(false);

        DeleteObjectsResult result = amazonS3Client.deleteObjects(multiObjectDeleteRequest);
        return result.getDeletedObjects().size();
    }
}
