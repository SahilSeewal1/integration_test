package com.kotak.ra.uams.unit.service;

import static com.kotak.ra.uams.unit.data.AccountStatusHistoryDetailsServiceData.USER_ACCOUNT_STATUS_CHANGE_HISTORY;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.kotak.ra.uams.dao.AccountStatusChangeHistoryDao;
import com.kotak.ra.uams.service.impl.AccountStatusHistoryDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type Account status history details service test. */
public class AccountStatusHistoryDetailsServiceTest {

  /** The Account status change history dao. */
  @Mock private AccountStatusChangeHistoryDao accountStatusChangeHistoryDao;

  /** The Account status history details service. */
  @InjectMocks private AccountStatusHistoryDetailsServiceImpl accountStatusHistoryDetailsService;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successfully fetch user account history. */
  @Test
  @DisplayName("Test to fetch user account history successfully.")
  public void successfullyFetchUserAccountHistory() {
    doNothing()
        .when(accountStatusChangeHistoryDao)
        .setAccountStatusHistoryItem(USER_ACCOUNT_STATUS_CHANGE_HISTORY);
    accountStatusHistoryDetailsService.setAccountStatusHistoryDetails(
        USER_ACCOUNT_STATUS_CHANGE_HISTORY);
    verify(accountStatusChangeHistoryDao)
        .setAccountStatusHistoryItem(USER_ACCOUNT_STATUS_CHANGE_HISTORY);
  }
}
