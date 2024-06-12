package com.kotak.ra.uams.integration.helper;

import static com.kotak.ra.uams.integration.constants.ConfigConstants.USER_DETAILS_TEST_BEAN;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.integration.integrationtests.initializer.IntegrationTestInitializer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.localstack.LocalStackContainer;
import software.amazon.awssdk.services.kms.KmsClient;

/** The type Aws services object helper. */
@Component
public class AwsServicesObjectHelper {
  @Autowired
  @Qualifier(USER_DETAILS_TEST_BEAN)
  private DynamoDBMapper userDetailsMapper;

  @Autowired
  @Qualifier("KmsConfigTestBean")
  private KmsClient awsKmsCLient;

  @PostConstruct
  public void init() {
    IntegrationTestInitializer.awsKmsCLient = awsKmsCLient;
    IntegrationTestInitializer.userDetailsMapper = userDetailsMapper;
  }
}
