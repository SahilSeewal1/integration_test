package com.kotak.ra.uams.integration.integrationtests.dormancy;

import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.AUTHORIZATION_KEY;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.X_REQUEST_ID_KEY;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.X_REQUEST_ID_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kotak.ra.uams.integration.integrationtests.UamsBaseIntegrationTest;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.UpdateUserAccountStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Log4j2
public class AccountDormancyTest extends UamsBaseIntegrationTest {
  @Autowired private MockMvc mockMvc;

  @Test
  @Order(1)
  @DisplayName("Test to update account status successfully to DORMANT")
  void isUpdatingAccountStatusToDormantSuccessful() {
    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(X_REQUEST_ID_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId("19kungvqs1dmi2q335nfjgta9m")
                                  .userId("KMBL123"))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToDormantSuccessful : {}",
          exception);
    }
  }

  @Test
  @Order(2)
  @DisplayName("Test to update account status unsuccessfully to DORMANT")
  void isUpdatingAccountStatusToDormantUnSuccessful() {

    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(X_REQUEST_ID_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId("19kungvqs1dmi2q335nfjgta9m")
                                  .userId("KMBL123"))))
          .andExpect(status().is4xxClientError())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToDormantSuccessful : {}",
          exception);
    }
  }
}
