package com.kotak.ra.uams.integration.integrationtests;

import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_DYNAMO_FILE_NAME;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_DYNAMO_FILE_PATH_IN_CONTAINER;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_KMS_FILE_NAME;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.INIT_KMS_FILE_PATH_IN_CONTAINER;
import static com.kotak.ra.uams.integration.constants.TestcontainersConstants.LOCALSTACK_IMAGE_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.InternetProtocol;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Getter
@Testcontainers
@Log4j2
public class UamsBaseIntegrationTest {
  @Autowired private ObjectMapper objectMapper;

  @Value("${authentication.crms.token}")
  private String authToken;

  @Container static LocalStackContainer localStack = UamsBaseIntegrationTest.localStackContainer();

  public static LocalStackContainer localStackContainer() {
    log.info("Executing test container to create dynamodb tables and KMS");

    LocalStackContainer localStackContainer =
        new LocalStackContainer(LOCALSTACK_IMAGE_NAME)
            .withServices(LocalStackContainer.Service.DYNAMODB, LocalStackContainer.Service.KMS)
            .withCopyFileToContainer(
                MountableFile.forClasspathResource(INIT_DYNAMO_FILE_NAME, 0744),
                INIT_DYNAMO_FILE_PATH_IN_CONTAINER)
            .waitingFor(Wait.forLogMessage(".*Executed init-dynamodb.sh.*", 1))
            .withCopyFileToContainer(
                MountableFile.forClasspathResource(INIT_KMS_FILE_NAME, 0744),
                INIT_KMS_FILE_PATH_IN_CONTAINER)
            .waitingFor(Wait.forLogMessage(".*Executed init-kms.sh.*", 1));
    localStackContainer.setPortBindings(
        List.of(String.format("%d:%d/%s", 4566, 4566, InternetProtocol.TCP)));
    return localStackContainer;
  }
}
