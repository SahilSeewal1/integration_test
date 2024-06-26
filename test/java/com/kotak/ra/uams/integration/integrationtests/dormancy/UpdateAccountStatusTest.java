package com.kotak.ra.uams.integration.integrationtests.dormancy;

import static com.kotak.ra.uams.constant.HeaderConstants.AUTHORIZATION_KEY;
import static com.kotak.ra.uams.constant.HeaderConstants.REQUEST_ID_HEADER_KEY;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_ACTIVE_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.X_REQUEST_ID_VALUE;
import static com.kotak.ra.uams.integration.data.RequestData.APPROVER_ID;
import static com.kotak.ra.uams.integration.data.RequestData.CLIENT_ID;
import static com.kotak.ra.uams.integration.data.RequestData.SCHEDULER_ID;
import static com.kotak.ra.uams.integration.data.RequestData.SCHEDULER_REMARK;
import static com.kotak.ra.uams.integration.data.RequestData.USER_REMARK;
import static com.kotak.ra.uams.integration.data.UserDetailsData.USER_DETAILS_ACTIVE;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kotak.ra.uams.integration.integrationtests.UamsBaseIntegrationTest;
import com.kotak.ra.uams.integration.integrationtests.initializer.IntegrationTestInitializer;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.UpdateUserAccountStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Log4j2
public class UpdateAccountStatusTest extends UamsBaseIntegrationTest {
  @Autowired private MockMvc mockMvc;
   @Autowired private IntegrationTestInitializer initializer;

    @BeforeAll
    public void setup() {
      initializer.setupResources();
    }

  @Test
  @Order(1)
  @DisplayName("Test to update account status successfully to DORMANT")
  void isUpdatingAccountStatusToDormantSuccessful() {
    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId(CLIENT_ID)
                                  .userId(USER_DETAILS_ACTIVE.get(1).getUserId())
                                  .remark(SCHEDULER_REMARK)
                                  .approverId(SCHEDULER_ID))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToDormantSuccessful : {}",
          exception);
      fail("Error occurred while executing test isUpdatingAccountStatusToDormantSuccessful");
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
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId(CLIENT_ID)
                                  .userId(USER_DETAILS_ACTIVE.get(1).getUserId())
                                  .remark(SCHEDULER_REMARK)
                                  .approverId(SCHEDULER_ID))))
          .andExpect(status().is4xxClientError())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToDormantUnSuccessful : {}",
          exception);
      fail("Error occurred while executing test isUpdatingAccountStatusToDormantUnSuccessful");
    }
  }

  @Test
  @Order(3)
  @DisplayName("Test to update account status successfully to ACTIVE")
  void isUpdatingAccountStatusToActiveSuccessful() {

    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.ACTIVE)
                                  .clientId(CLIENT_ID)
                                  .userId(USER_DETAILS_ACTIVE.get(0).getUserId())
                                  .remark(USER_REMARK)
                                  .approverId(APPROVER_ID))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToActiveSuccessful : {}",
          exception);
      fail("Error occurred while executing test isUpdatingAccountStatusToActiveSuccessful");
    }
  }

  @Test
  @Order(4)
  @DisplayName("Test to update account status unsuccessfully to ACTIVE")
  void isUpdatingAccountStatusToActiveUnSuccessful() {

    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.ACTIVE)
                                  .clientId(CLIENT_ID)
                                  .userId(USER_DETAILS_ACTIVE.get(0).getUserId())
                                  .remark(USER_REMARK)
                                  .approverId(APPROVER_ID))))
          .andExpect(status().is4xxClientError())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(UPDATE_ACCOUNT_STATUS_AS_ACTIVE_FAILURE));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusToActiveUnSuccessful : {}",
          exception);
      fail("Error occurred while executing test isUpdatingAccountStatusToActiveUnSuccessful");
    }
  }

  @Test
  @Order(5)
  @DisplayName("Test to update account status with missing required params.")
  void isUpdatingAccountStatusWithMissingRequestParams() {

    try {
      mockMvc
          .perform(
              post("/updateUserAccountStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UpdateUserAccountStatusRequest()
                                  .accountStatus(AccountStatus.ACTIVE)
                                  .clientId(CLIENT_ID)
                                  .userId(USER_DETAILS_ACTIVE.get(0).getUserId())
                                  .remark(USER_REMARK))))
          .andExpect(status().is4xxClientError())
          .andExpect(
              MockMvcResultMatchers.jsonPath("$.message")
                  .value(
                      "The 'approverId' field with the value is not valid, Please check the field and try again."));
    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isUpdatingAccountStatusWithMissingRequestParams : {}",
          exception);
      fail("Error occurred while executing test isUpdatingAccountStatusWithMissingRequestParams");
    }
  }
}
