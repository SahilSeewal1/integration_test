package com.kotak.ra.uams.integration.configuration;

import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_FILE_NAME;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_FILE_PATH_IN_CONTAINER;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.LOCALSTACK_IMAGE_NAME;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
@Log4j2
public class TestcontainersConfig {

  @Bean
  public LocalStackContainer localStackContainer() {
    log.info("Executing test container to create dynamodb tables and KMS");

    return new LocalStackContainer(LOCALSTACK_IMAGE_NAME)
        .withServices(LocalStackContainer.Service.DYNAMODB, LocalStackContainer.Service.KMS)
        // .withReuse(true)
        .withCopyFileToContainer(
            MountableFile.forClasspathResource(INIT_FILE_NAME, 0744), INIT_FILE_PATH_IN_CONTAINER)
        .waitingFor(Wait.forLogMessage(".*Executed init-dynamodb.sh.*", 1))
        .withCopyFileToContainer(
            MountableFile.forClasspathResource("init-kms.sh", 0744),
            "/etc/localstack/init/ready.d/init-kms.sh")
        .waitingFor(Wait.forLogMessage(".*Executed init-kms.sh.*", 1));
  }
}
