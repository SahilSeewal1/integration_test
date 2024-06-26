package com.kotak.ra.uams.unit.service;

import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.ACTIVE_USER_ACCOUNT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.ACTIVE_ACCOUNT_DETAILS;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.PAGINATED_ITEMS_ACTIVE_ACCOUNT;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.UPDATE_USER_ACCOUNT_STATUS_RESPONSE_ACTIVE;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.UPDATE_USER_ACCOUNT_STATUS_RESPONSE_DORMANT;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.USER_ACCOUNT_DETAILS_BY_ACTIVE_STATUS_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.kotak.ra.uams.entity.AccountDetails;
import com.kotak.ra.uams.helper.ObjectHelper;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.service.AccountStatusHistoryDetailsService;
import com.kotak.ra.uams.service.UserDetailsService;
import com.kotak.ra.uams.service.impl.AccountDormancyServiceImpl;
import java.util.InputMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AccountDormancyServiceTest {

  @Mock private UserDetailsService userDetailsService;

  @Mock private ObjectHelper objectHelper;

  @Mock private AccountStatusHistoryDetailsService accountStatusHistoryDetailsService;

  /** The Account dormancy controller. */
  @InjectMocks private AccountDormancyServiceImpl accountDormancyService;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successful fetch active user account details test. */
  @Test
  @DisplayName("Test in case of successful fetch of user account details.")
  public void successfullyGetAccountDetailsTest() {
    when(userDetailsService.getUserAccountDetailsByAccountStatus(ACTIVE_USER_ACCOUNT_REQUEST))
        .thenReturn(PAGINATED_ITEMS_ACTIVE_ACCOUNT);
    assertEquals(
        USER_ACCOUNT_DETAILS_BY_ACTIVE_STATUS_RESPONSE,
        accountDormancyService.getAccountDetails(ACTIVE_USER_ACCOUNT_REQUEST));
  }

  /** Successful update user account status as ACTIVE test. */
  @Test
  @DisplayName("Test to mark user account status as ACTIVE successfully.")
  public void successfullyUpdateAccountStatusAsActiveTest() {
    AccountDetails accountDetails = ACTIVE_ACCOUNT_DETAILS.get(0);
    accountDetails.setAccountStatus(AccountStatus.DORMANT);
    when(userDetailsService.getUserAccountDetails(
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getClientId(),
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getUserId(),
            AccountDetails.class))
        .thenReturn(accountDetails);
    doNothing().when(userDetailsService).setUserAccountDetails(any());
    doNothing().when(accountStatusHistoryDetailsService).setAccountStatusHistoryDetails(any());
    assertEquals(
        UPDATE_USER_ACCOUNT_STATUS_RESPONSE_ACTIVE,
        accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST));
  }

  /** Successful update user account status as DORMANT test. */
  @Test
  @DisplayName("Test to mark user account status as DORMANT successfully.")
  public void successfullyUpdateAccountStatusAsDormantTest() {
    AccountDetails accountDetails = ACTIVE_ACCOUNT_DETAILS.get(0);
    when(userDetailsService.getUserAccountDetails(
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getClientId(),
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getUserId(),
            AccountDetails.class))
        .thenReturn(accountDetails);
    doNothing().when(userDetailsService).setUserAccountDetails(any());
    doNothing().when(accountStatusHistoryDetailsService).setAccountStatusHistoryDetails(any());
    assertEquals(
        UPDATE_USER_ACCOUNT_STATUS_RESPONSE_DORMANT,
        accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST));
  }

  /** Mark account status as ACTIVE when it is already ACTIVE. */
  @Test
  @DisplayName("Test to mark account as active when it is already active.")
  public void accountStatusAlreadyActiveTest() {
    AccountDetails accountDetails = ACTIVE_ACCOUNT_DETAILS.get(0);
    when(userDetailsService.getUserAccountDetails(
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getClientId(),
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getUserId(),
            AccountDetails.class))
        .thenReturn(accountDetails);
    doNothing().when(userDetailsService).setUserAccountDetails(any());
    doNothing().when(accountStatusHistoryDetailsService).setAccountStatusHistoryDetails(any());

    assertThrows(
        InputMismatchException.class,
        () -> {
          accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST);
        });
  }

  /** Mark account status as DORMANT when it is already DORMANT. */
  @Test
  @DisplayName("Test to mark account as dormant when it is already dormant.")
  public void accountStatusAlreadyDormantTest() {
    AccountDetails accountDetails = ACTIVE_ACCOUNT_DETAILS.get(0);
    accountDetails.setAccountStatus(AccountStatus.DORMANT);
    when(userDetailsService.getUserAccountDetails(
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getClientId(),
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getUserId(),
            AccountDetails.class))
        .thenReturn(accountDetails);
    doNothing().when(userDetailsService).setUserAccountDetails(any());
    doNothing().when(accountStatusHistoryDetailsService).setAccountStatusHistoryDetails(any());

    assertThrows(
        InputMismatchException.class,
        () -> {
          accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST);
        });
  }

  /** No user account found test. */
  @Test
  @DisplayName("Test when no user account found.")
  public void noRecordFoundTest() {
    when(userDetailsService.getUserAccountDetails(
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getClientId(),
            UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST.getUserId(),
            AccountDetails.class))
        .thenReturn(null);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          accountDormancyService.updateAccountStatus(UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST);
        });
  }
}
