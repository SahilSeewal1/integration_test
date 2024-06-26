package com.kotak.ra.uams.unit.controller;

import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.ACTIVE_USER_ACCOUNT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.ACTIVE_USER_ACCOUNT_RESPONSE;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.DORMANT_USER_ACCOUNT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.DORMANT_USER_ACCOUNT_RESPONSE;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_ACTIVE_RESPONSE;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_ALREADY_ACTIVE_RESPONSE;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_ALREADY_DORMANT_RESPONSE;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_DORMANT_RESPONSE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.REQUEST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.kotak.ra.uams.controller.AccountDormancyController;
import com.kotak.ra.uams.service.AccountDormancyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** The type Account dormancy controller test. */
public class AccountDormancyControllerTest {

  /** The Account dormancy service. */
  @Mock private AccountDormancyService accountDormancyService;

  /** The Account dormancy controller. */
  @InjectMocks private AccountDormancyController accountDormancyController;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successful fetch active user account details test. */
  @Test
  @DisplayName("Test in case of successful fetch of users by account status as ACTIVE")
  public void successfulFetchActiveUserAccountDetailsTest() {
    when(accountDormancyService.getAccountDetails(ACTIVE_USER_ACCOUNT_REQUEST))
        .thenReturn(ACTIVE_USER_ACCOUNT_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK).body(ACTIVE_USER_ACCOUNT_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._getListOfUserAccountDetailsByStatus(
            REQUEST_ID, ACTIVE_USER_ACCOUNT_REQUEST));
  }

  /** Successful fetch dormant user account details test. */
  @Test
  @DisplayName("Test in case of successful fetch of users by account status as DORMANT")
  public void successfulFetchDormantUserAccountDetailsTest() {
    when(accountDormancyService.getAccountDetails(DORMANT_USER_ACCOUNT_REQUEST))
        .thenReturn(DORMANT_USER_ACCOUNT_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK).body(DORMANT_USER_ACCOUNT_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._getListOfUserAccountDetailsByStatus(
            REQUEST_ID, DORMANT_USER_ACCOUNT_REQUEST));
  }

  /** Successful update account status active test. */
  @Test
  @DisplayName("Test in case of successful updating account status as ACTIVE")
  public void successfulUpdateAccountStatusActiveTest() {
    when(accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST))
        .thenReturn(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK).body(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._updateUserAccountStatus(
            REQUEST_ID, UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST));
  }

  /** Successful update account status dormant test. */
  @Test
  @DisplayName("Test in case of successful updating account status as DORMANT")
  public void successfulUpdateAccountStatusDormantTest() {
    when(accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST))
        .thenReturn(UPDATE_USER_ACCOUNT_STATUS_DORMANT_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK).body(UPDATE_USER_ACCOUNT_STATUS_DORMANT_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._updateUserAccountStatus(
            REQUEST_ID, UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST));
  }

  /** Account status already active test. */
  @Test
  @DisplayName("Test in case of account status already ACTIVE")
  public void accountStatusAlreadyActiveTest() {
    when(accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST))
        .thenReturn(UPDATE_USER_ACCOUNT_STATUS_ALREADY_ACTIVE_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK)
            .body(UPDATE_USER_ACCOUNT_STATUS_ALREADY_ACTIVE_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._updateUserAccountStatus(
            REQUEST_ID, UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST));
  }

  /** Account status already dormant test. */
  @Test
  @DisplayName("Test in case of account status already DORMANT")
  public void accountStatusAlreadyDormantTest() {
    when(accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST))
        .thenReturn(UPDATE_USER_ACCOUNT_STATUS_ALREADY_DORMANT_RESPONSE);
    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK)
            .body(UPDATE_USER_ACCOUNT_STATUS_ALREADY_DORMANT_RESPONSE);
    assertEquals(
        expected,
        accountDormancyController._updateUserAccountStatus(
            REQUEST_ID, UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST));
  }
}
