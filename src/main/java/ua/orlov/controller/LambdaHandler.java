package ua.orlov.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import ua.orlov.model.Request;
import ua.orlov.model.Response;

import static ua.orlov.service.S3AWSService.saveFile;
import static ua.orlov.service.SecretAWSService.getDirectoryPath;

public class LambdaHandler implements RequestHandler<Request, Response> {

    @Override
    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        String secretName = request.secret();
        String message = request.message();
        String bucketName = "default";
        try {
            bucketName = getDirectoryPath(secretName);
            saveFile(bucketName, message, logger);
            return new Response("Message stored successfully in bucket: " + bucketName);
        } catch (Exception e) {
            logger.log("Error storing message: " + e.getMessage());
            return new Response("Error storing message in bucket: " + bucketName);
        }
    }
}
