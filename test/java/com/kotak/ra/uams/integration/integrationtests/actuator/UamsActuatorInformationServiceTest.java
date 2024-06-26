package com.kotak.ra.uams.integration.integrationtests.actuator;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kotak.ra.uams.integration.integrationtests.UamsBaseIntegrationTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Log4j2
public class UamsActuatorInformationServiceTest extends UamsBaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Health status of UAMS application")
  void healthCheck() {
    try {
      this.mockMvc
          .perform(get("/actuator/health").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    } catch (Exception exception) {
      log.error("Error occurred while executing test healthCheck : {}", exception);
      fail("Error occurred while executing test healthCheck");
    }
  }

  @Test
  @DisplayName("API mappings of UAMS application")
  void mappingCheck() {
    try {
      this.mockMvc
          .perform(get("/actuator/mappings").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    } catch (Exception exception) {
      log.error("Error occurred while executing test mappingCheck : {}", exception);
      fail("Error occurred while executing test mappingCheck");
    }
  }

  @Test
  @DisplayName("Invalid actuator API for UAMS application")
  void notFoundCheck() {
    try {
      this.mockMvc
          .perform(get("/actuator/health1").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception exception) {
      log.error("Error occurred while executing test notFoundCheck : {}", exception);
      fail("Error occurred while executing test notFoundCheck");
    }
  }
}
