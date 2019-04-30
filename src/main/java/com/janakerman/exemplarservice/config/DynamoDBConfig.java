package com.janakerman.exemplarservice.config;


import com.amazonaws.regions.Regions;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.janakerman.exemplarservice.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint:}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accessKeyId:}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretKey:}")
    private String amazonAWSSecretKey;

    @Value("${amazon.aws.region}")
    private String awsRegion;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClient amazonDynamoDB;

        if (!amazonAWSAccessKey.isEmpty() && !amazonAWSSecretKey.isEmpty()) {
            amazonDynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
        } else {
            amazonDynamoDB = new AmazonDynamoDBClient();
        }

        if (!amazonDynamoDBEndpoint.isEmpty()) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        if (!awsRegion.isEmpty()) {
            amazonDynamoDB.withRegion(Regions.fromName(awsRegion));
        }

        return amazonDynamoDB;
    }

}
