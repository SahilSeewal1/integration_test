package com.kotak.ra.uams.integration.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

@TestConfiguration(proxyBeanMethods = false)
@Log4j2
public class AwsKmsConfig {

  @Bean(name = "KmsConfigTestBean")
  public KmsClient awsKmsCLient(final LocalStackContainer localStackContainer) {
    return KmsClient.builder()
        .region(Region.of(localStackContainer.getRegion()))
        .endpointOverride(localStackContainer.getEndpoint())
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    localStackContainer.getAccessKey(), localStackContainer.getSecretKey())))
        .build();
  }
}
