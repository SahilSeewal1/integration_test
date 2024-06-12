package com.kotak.ra.uams.unit.service;

import static com.kotak.ra.uams.constant.DbConstants.ID_DELIMITER;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.ACTIVE_USER_ACCOUNT_REQUEST;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.CLIENT_ID;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.PAGINATED_ITEMS_ACTIVE_ACCOUNT;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.PAGINATED_ITEMS_DORMANT_ACCOUNT;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.USER_ACCOUNT_DETAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kotak.ra.uams.dao.UserDetailsDao;
import com.kotak.ra.uams.entity.AccountDetails;
import com.kotak.ra.uams.helper.ObjectHelper;
import com.kotak.ra.uams.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type User details service test. */
public class UserDetailsServiceTest {

  /** The Object helper. */
  @Mock private ObjectHelper objectHelper;

  /** The User details dao. */
  @Mock private UserDetailsDao userDetailsDao;

  /** The Account dormancy controller. */
  @InjectMocks private UserDetailsServiceImpl userDetailsService;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successfully fetch paginated list of items by account status as ACTIVE. */
  @Test
  @DisplayName(
      "Test in case of fetching paginated list of items with account status as ACTIVE successfully.")
  public void fetchPaginatedAccountDetailsWithActiveTest() {
    when(userDetailsDao.getPaginatedUserAccountDetailsByIndex(any()))
        .thenReturn(PAGINATED_ITEMS_ACTIVE_ACCOUNT);
    assertEquals(
        PAGINATED_ITEMS_ACTIVE_ACCOUNT,
        userDetailsService.getUserAccountDetailsByAccountStatus(ACTIVE_USER_ACCOUNT_REQUEST));
  }

  /** Successfully fetch paginated list of items by account status as DORMANT. */
  @Test
  @DisplayName(
      "Test in case of fetching paginated list of items with account status as DORMANT successfully.")
  public void fetchPaginatedAccountDetailsWithDormantTest() {
    when(userDetailsDao.getPaginatedUserAccountDetailsByIndex(any()))
        .thenReturn(PAGINATED_ITEMS_DORMANT_ACCOUNT);
    assertEquals(
        PAGINATED_ITEMS_DORMANT_ACCOUNT,
        userDetailsService.getUserAccountDetailsByAccountStatus(ACTIVE_USER_ACCOUNT_REQUEST));
  }

  /** Fetch single user account detail test. */
  @Test
  @DisplayName(
      "Test in case of fetching paginated list of items with account status as DORMANT successfully.")
  public void fetchSingleUserAccountDetailTest() {
    when(userDetailsDao.getUserDetailsItem(
            new StringBuilder()
                .append(CLIENT_ID)
                .append(ID_DELIMITER)
                .append(USER_ACCOUNT_DETAIL.getUserId())
                .toString(),
            AccountDetails.class))
        .thenReturn(USER_ACCOUNT_DETAIL);
    assertEquals(
        USER_ACCOUNT_DETAIL,
        userDetailsService.getUserAccountDetails(
            CLIENT_ID, USER_ACCOUNT_DETAIL.getUserId(), AccountDetails.class));
  }

  /** Sets user account details test. */
  @Test
  @DisplayName("Test in case of saving user account details successfully.")
  public void setUserAccountDetailsTest() {
    doNothing().when(userDetailsDao).setUserDetailsItem(USER_ACCOUNT_DETAIL);
    userDetailsService.setUserAccountDetails(USER_ACCOUNT_DETAIL);
    verify(userDetailsDao).setUserDetailsItem(USER_ACCOUNT_DETAIL);
  }
}
