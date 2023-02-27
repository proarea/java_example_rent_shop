package com.snowboard_rental_crm.media_core.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.snowboard_rental_crm.shared_module.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

import static com.restaurant_crm.media_data.constant.ExceptionConstants.FILE_NOT_FOUND;


@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    public void uploadFile(File file, final String fileName, final String filePath) {
        try {
            amazonS3Client.putObject(filePath, fileName, file);
        } catch (AmazonServiceException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    public InputStream getFileFromBucket(final String fileName, final String filePath) {
        ValidationUtil.validateOrFileNotFound(
                amazonS3Client.doesObjectExist(filePath, fileName),
                String.format(FILE_NOT_FOUND, fileName)
        );

        return amazonS3Client.getObject(filePath, fileName).getObjectContent();
    }

    public void deleteFile(final String fileName, final String filePath) {
        amazonS3Client.deleteObject(filePath, fileName);
    }
}
