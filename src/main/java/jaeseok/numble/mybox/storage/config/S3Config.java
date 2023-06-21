package jaeseok.numble.mybox.storage.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jaeseok.numble.mybox.storage.AmazonS3StorageHandler;
import jaeseok.numble.mybox.storage.StorageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prd")
@Configuration
public class S3Config {

    @Bean
    public StorageHandler storageHandler(
            @Value("${cloud.aws.credentials.access-key}") String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${cloud.aws.region.static}") String region,
            @Value("${cloud.aws.s3.bucket}") String bucket) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3Client amazonS3Client =  (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        return new AmazonS3StorageHandler(amazonS3Client, bucket);
    }
}
