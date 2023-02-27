package com.snowboard_rental_crm.media_core.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.snowboard_rental_crm.media_core.config.property.AWSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Bean
    public AmazonS3Client amazonS3(AWSProperties awsProperties) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsProperties.getKey(), awsProperties.getSecret());
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder
                .EndpointConfiguration(awsProperties.getServiceEndpoint(), awsProperties.getRegion());

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withPathStyleAccessEnabled(true)
                .withCredentials(credentialsProvider)
                .build();

        if (!s3client.doesBucketExistV2(awsProperties.getBucket())) {
            s3client.createBucket(awsProperties.getBucket());
        }

        return (AmazonS3Client) s3client;
    }
}
