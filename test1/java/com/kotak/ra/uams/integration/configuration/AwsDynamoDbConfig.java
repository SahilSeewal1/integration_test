package com.kotak.ra.uams.integration.configuration;

import static com.kotak.ra.uams.integration.constants.ConfigConstants.ACCOUNT_STATUS_CHANGE_HISTORY_TEST_BEAN;
import static com.kotak.ra.uams.integration.constants.ConfigConstants.USER_DETAILS_TEST_BEAN;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.helper.DdbConfigHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;

/** The type Aws dynamo db config. */
@TestConfiguration(proxyBeanMethods = false)
@Log4j2
public class AwsDynamoDbConfig {
  @Autowired private DdbConfigHelper ddbConfigHelper;
  @Autowired private LocalStackContainer localStackContainer;

  @Value("${amazon.dynamodb.tablename.useraccessdetails}")
  private String USER_DETAILS_TEST_TABLE_NAME;

  /** The constant USER_ACCOUNT_STATUS_CHANGE_HISTORY_TABLE_NAME. */
  @Value("${amazon.dynamodb.tablename.useraccountstatuschangehistory}")
  private String USER_ACCOUNT_STATUS_CHANGE_HISTORY_TEST_TABLE_NAME;

  /**
   * Aws credentials aws credentials.
   *
   * @param localStackContainer the local stack container
   * @return the aws credentials
   */
  @Bean
  public AWSCredentials awsCredentials(LocalStackContainer localStackContainer) {
    return new BasicAWSCredentials(
        localStackContainer.getAccessKey(), localStackContainer.getSecretKey());
  }

  /**
   * Aws dynamo db amazon dynamo db.
   *
   * @return the amazon dynamo db
   */
  @Bean
  public AmazonDynamoDB awsDynamoDb() {
    System.setProperty("amazon.dynamodb.endpoint", localStackContainer.getEndpoint().toString());
    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(localStackContainer)))
        .withEndpointConfiguration(
            new AwsClientBuilder.EndpointConfiguration(
                localStackContainer.getEndpoint().toString(), localStackContainer.getRegion()))
        .build();
  }

  /**
   * Amazon dynamo db mapper for user details dynamo db mapper.
   *
   * @return the dynamo db mapper
   */
  @Bean(USER_DETAILS_TEST_BEAN)
  public DynamoDBMapper amazonDynamoDbMapperForUserDetails() {
    return new DynamoDBMapper(
        awsDynamoDb(), ddbConfigHelper.dynamoDbMapperConfig(USER_DETAILS_TEST_TABLE_NAME));
  }

  /**
   * Amazon dynamo db mapper for account status change history dynamo db mapper.
   *
   * @return the dynamo db mapper
   */
  @Bean(ACCOUNT_STATUS_CHANGE_HISTORY_TEST_BEAN)
  public DynamoDBMapper amazonDynamoDbMapperForAccountStatusChangeHistory() {
    return new DynamoDBMapper(
        awsDynamoDb(),
        ddbConfigHelper.dynamoDbMapperConfig(USER_ACCOUNT_STATUS_CHANGE_HISTORY_TEST_TABLE_NAME));
  }
}
