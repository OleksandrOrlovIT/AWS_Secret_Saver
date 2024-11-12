package ua.orlov.service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretAWSService {

    public static String getDirectoryPath(String secretKey) {
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .region(Region.EU_NORTH_1)
                .build();

        GetSecretValueRequest secretRequest = GetSecretValueRequest.builder()
                .secretId(secretKey)
                .build();
        GetSecretValueResponse secretResponse = secretsClient.getSecretValue(secretRequest);

        return getValue(secretResponse.secretString());
    }

    private static String getValue(String rawString) {
        rawString = rawString.replaceAll("[\\\\\"{}]", "");
        String[] parts = rawString.split(":");

        return parts[1];
    }
}
