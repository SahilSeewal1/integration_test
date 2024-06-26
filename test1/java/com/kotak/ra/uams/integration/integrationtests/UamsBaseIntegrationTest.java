package com.kotak.ra.uams.integration.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotak.ra.uams.integration.integrationtests.initializer.InitializeIntegrationTest;
import lombok.Getter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@InitializeIntegrationTest
@Getter
public class UamsBaseIntegrationTest {
  @Autowired private ObjectMapper objectMapper;

  @Value("${authentication.crms.token}")
  private String authToken;
}
