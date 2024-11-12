package ua.orlov.service;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class S3AWSService {

    public static void saveFile(String bucketName, String message, LambdaLogger logger) throws IOException {
        S3Client s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1)
                .build();
        String fileKey = "message " + LocalDate.now() + ".txt";
        Path tempFilePath = Paths.get("/tmp/" + fileKey);
        Files.writeString(tempFilePath, message);

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .ifNoneMatch("*")
                        .build(),
                tempFilePath);

        logger.log("Message stored in S3 bucket: " + bucketName + " as " + fileKey);
    }
}
