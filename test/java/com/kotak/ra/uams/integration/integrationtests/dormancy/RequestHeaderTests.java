package com.kotak.ra.uams.integration.integrationtests.dormancy;

import static com.kotak.ra.uams.constant.HeaderConstants.AUTHORIZATION_KEY;
import static com.kotak.ra.uams.constant.HeaderConstants.REQUEST_ID_HEADER_KEY;
import static com.kotak.ra.uams.integration.constants.HeaderConstants.INVALID_X_REQUEST_ID_VALUE;
import static com.kotak.ra.uams.integration.data.RequestData.CLIENT_ID;
import static com.kotak.ra.uams.integration.data.RequestData.PAGE_SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kotak.ra.uams.integration.integrationtests.UamsBaseIntegrationTest;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.UserAccountDetailsByStatusRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Log4j2
public class RequestHeaderTests extends UamsBaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Test to fetch user account details when JWT token is invalid")
  void isJwtTokenInvalid() {
    try {
      mockMvc
          .perform(
              post("/fetchUserAccountDetailsByStatus")
                  .contentType(MediaType.APPLICATION_JSON)
                  .header(REQUEST_ID_HEADER_KEY, INVALID_X_REQUEST_ID_VALUE)
                  .header(AUTHORIZATION_KEY, getAuthToken())
                  .content(
                      getObjectMapper()
                          .writeValueAsString(
                              new UserAccountDetailsByStatusRequest()
                                  .accountStatus(AccountStatus.DORMANT)
                                  .clientId(CLIENT_ID)
                                  .pageSize(PAGE_SIZE))))
          .andExpect(status().is4xxClientError())
          .andExpect(jsonPath("$.message", is("Please provide valid Authentication Token")));

    } catch (Exception exception) {
      log.error("Error occurred while executing test isJwtTokenInvalid : {}", exception);
      fail("Error occurred while executing test isJwtTokenInvalid");
    }
  }
}
