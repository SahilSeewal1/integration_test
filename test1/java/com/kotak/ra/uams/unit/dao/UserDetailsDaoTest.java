package com.kotak.ra.uams.unit.dao;

import static com.kotak.ra.uams.constant.DbConstants.USER_DETAILS_TABLE_BEAN;
import static com.kotak.ra.uams.unit.data.AccountDormancyServiceData.PAGINATED_ITEMS_ACTIVE_ACCOUNT;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ID;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.USER_DETAILS_ACTIVE;
import static com.kotak.ra.uams.unit.data.UserDetailsDaoData.DDB_QUERY_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.dao.AwsDynamoDbDao;
import com.kotak.ra.uams.dao.impl.UserDetailsDaoImpl;
import com.kotak.ra.uams.entity.UserDetails;
import com.kotak.ra.uams.model.PaginatedItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type User details dao test. */
public class UserDetailsDaoTest {
  @Mock private AwsDynamoDbDao awsDynamoDbDao;

  /** The User details mapper. */
  @Mock(name = USER_DETAILS_TABLE_BEAN)
  private DynamoDBMapper userDetailsMapper;

  /** The User details dao. */
  @InjectMocks private UserDetailsDaoImpl userDetailsDao;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successfully fetch paginated user account details test. */
  @Test
  @DisplayName("Successful fetch paginated account details.")
  public void successfullyFetchPaginatedUserDetails() {

    when(awsDynamoDbDao.getPaginatedItemsByIndexQuery(DDB_QUERY_REQUEST, userDetailsMapper))
        .thenReturn((PaginatedItems) PAGINATED_ITEMS_ACTIVE_ACCOUNT);
    assertEquals(
        PAGINATED_ITEMS_ACTIVE_ACCOUNT,
        userDetailsDao.getPaginatedUserAccountDetailsByIndex(DDB_QUERY_REQUEST));
  }

  /** Successfully fetch single user detail. */
  @Test
  @DisplayName("Successful fetch single user details.")
  public void successfullyFetchSingleUserDetail() {

    when(awsDynamoDbDao.getItem(ID, UserDetails.class, userDetailsMapper))
        .thenReturn(USER_DETAILS_ACTIVE);
    assertEquals(USER_DETAILS_ACTIVE, userDetailsDao.getUserDetailsItem(ID, UserDetails.class));
  }

  /** Successfully save single user detail. */
  @Test
  @DisplayName("Successful save single user details.")
  public void successfullySaveSingleUserDetail() {

    doNothing().when(awsDynamoDbDao).setItem(USER_DETAILS_ACTIVE, userDetailsMapper);
    userDetailsDao.setUserDetailsItem(USER_DETAILS_ACTIVE);
    verify(awsDynamoDbDao).setItem(USER_DETAILS_ACTIVE, userDetailsMapper);
  }
}
