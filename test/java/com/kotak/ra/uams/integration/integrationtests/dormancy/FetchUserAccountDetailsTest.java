package com.kotak.ra.uams.integration.integrationtests.dormancy;

import static com.kotak.ra.uams.constant.HeaderConstants.AUTHORIZATION_KEY;
import static com.kotak.ra.uams.constant.HeaderConstants.REQUEST_ID_HEADER_KEY;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.X_REQUEST_ID_VALUE;
import static com.kotak.ra.uams.integration.data.RequestData.CLIENT_ID;
import static com.kotak.ra.uams.integration.data.RequestData.INVALID_CLIENT_ID;
import static com.kotak.ra.uams.integration.data.RequestData.LAST_LOGIN_END_TIME;
import static com.kotak.ra.uams.integration.data.RequestData.LAST_LOGIN_START_TIME;
import static com.kotak.ra.uams.integration.data.RequestData.PAGE_SIZE;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kotak.ra.uams.integration.integrationtests.UamsBaseIntegrationTest;
import com.kotak.ra.uams.integration.integrationtests.initializer.IntegrationTestInitializer;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.UserAccountDetailsByStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Log4j2
public class FetchUserAccountDetailsTest extends UamsBaseIntegrationTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private IntegrationTestInitializer initializer;

  @BeforeAll
  public void setup() {
    initializer.setupResources();
  }

  @Test
  @DisplayName("Test to fetch user account details with account status as DORMANT successfully ")
  void isFetchByAccountStatusAsDormantSuccessful() {
    try {
      mockMvc
          .perform(
              post("/fetchUserAccountDetailsByStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UserAccountDetailsByStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId(CLIENT_ID)
                                  .pageSize(PAGE_SIZE))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(jsonPath("$.accountsDetail[0].name", is("Ram Prakash")))
          .andExpect(jsonPath("$.accountsDetail[0].userId", is("KMBL123")))
          .andExpect(jsonPath("$.accountsDetail[0].accountStatus", is("DORMANT")))
          .andExpect(jsonPath("$.accountsDetail[0].statusUpdationTime", is(1618102796)))
          .andExpect(jsonPath("$.lastKeyReference.lastLoginTime", is(1686480396)))
          .andExpect(jsonPath("$.lastKeyReference.clientId", is("19kungvqs1dmi2q335nfjgta9m")))
          .andExpect(jsonPath("$.lastKeyReference.accountStatus", is("DORMANT")))
          .andExpect(jsonPath("$.lastKeyReference.userId", is("KMBL123")));

    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isFetchByAccountStatusAsDormantSuccessful : {}",
          exception);
      fail("Error occurred while executing test isFetchByAccountStatusAsDormantSuccessful");
    }
  }

  @Test
  @DisplayName("Test to fetch user account details with account status as ACTIVE successfully ")
  void isFetchByAccountStatusAsActiveSuccessful() {
    try {
      mockMvc
          .perform(
              post("/fetchUserAccountDetailsByStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UserAccountDetailsByStatusRequest()
                                  .accountStatus(AccountStatus.ACTIVE)
                                  .clientId(CLIENT_ID)
                                  .lastLoginStartTime(LAST_LOGIN_START_TIME)
                                  .lastLoginEndTime(LAST_LOGIN_END_TIME)
                                  .pageSize(PAGE_SIZE))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(jsonPath("$.accountsDetail[0].name", is("Robin Malhotra")))
          .andExpect(jsonPath("$.accountsDetail[0].userId", is("KMBL890")))
          .andExpect(jsonPath("$.accountsDetail[0].accountStatus", is("ACTIVE")))
          .andExpect(jsonPath("$.accountsDetail[0].statusUpdationTime", is(1234569890)))
          .andExpect(jsonPath("$.lastKeyReference.lastLoginTime", is(1686480396)))
          .andExpect(jsonPath("$.lastKeyReference.clientId", is("19kungvqs1dmi2q335nfjgta9m")))
          .andExpect(jsonPath("$.lastKeyReference.accountStatus", is("ACTIVE")))
          .andExpect(jsonPath("$.lastKeyReference.userId", is("KMBL890")));

    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isFetchByAccountStatusAsActiveSuccessful : {}",
          exception);
      fail("Error occurred while executing test isFetchByAccountStatusAsActiveSuccessful");
    }
  }

  @Test
  @DisplayName(
      "Test to fetch user account details with account status as ACTIVE but with missing parameters")
  void isFetchByAccountStatusAsActiveUnsuccessful() {
    try {
      mockMvc
          .perform(
              post("/fetchUserAccountDetailsByStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UserAccountDetailsByStatusRequest()
                                  .accountStatus(AccountStatus.ACTIVE)
                                  .clientId(CLIENT_ID)
                                  .pageSize(PAGE_SIZE))))
          .andExpect(status().is4xxClientError());

    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isFetchByAccountStatusAsActiveUnsuccessful : {}",
          exception);
      fail("Error occurred while executing test isFetchByAccountStatusAsActiveUnsuccessful");
    }
  }

  @Test
  @DisplayName("Test to fetch user account details when client id is invalid")
  void isFetchByAccountStatusWhenClientIdIsInvalid() {
    try {
      mockMvc
          .perform(
              post("/fetchUserAccountDetailsByStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UserAccountDetailsByStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId(INVALID_CLIENT_ID)
                                  .pageSize(PAGE_SIZE))))
          .andExpect(status().is2xxSuccessful())
          .andExpect(jsonPath("$.accountsDetail", is(empty())))
          .andExpect(jsonPath("$.lastKeyReference", nullValue()));

    } catch (Exception exception) {
      log.error(
          "Error occurred while executing test isFetchByAccountStatusAsActiveUnsuccessful : {}",
          exception);
      fail("Error occurred while executing test isFetchByAccountStatusAsActiveUnsuccessful");
    }
  }
}
