package com.kotak.ra.uams.unit.dao;

import static com.kotak.ra.uams.constant.DbConstants.ACCOUNT_STATUS_CHANGE_HISTORY_TABLE_BEAN;
import static com.kotak.ra.uams.unit.data.AccountStatusHistoryDetailsServiceData.USER_ACCOUNT_STATUS_CHANGE_HISTORY;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.dao.AwsDynamoDbDao;
import com.kotak.ra.uams.dao.impl.AccountStatusChangeHistoryDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type Account status change history dao test. */
public class AccountStatusChangeHistoryDaoTest {

  /** The Account status change history mapper. */
  @Mock(name = ACCOUNT_STATUS_CHANGE_HISTORY_TABLE_BEAN)
  private DynamoDBMapper accountStatusChangeHistoryMapper;

  /** The Aws dynamo db dao. */
  @Mock private AwsDynamoDbDao awsDynamoDbDao;

  /** The Account status change history dao. */
  @InjectMocks private AccountStatusChangeHistoryDaoImpl accountStatusChangeHistoryDao;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successfully save user details. */
  @Test
  @DisplayName("Successfully set account status history item test.")
  public void successfullySaveUserDetails() {
    doNothing()
        .when(awsDynamoDbDao)
        .setItem(USER_ACCOUNT_STATUS_CHANGE_HISTORY, accountStatusChangeHistoryMapper);
    accountStatusChangeHistoryDao.setAccountStatusHistoryItem(USER_ACCOUNT_STATUS_CHANGE_HISTORY);
    verify(awsDynamoDbDao)
        .setItem(USER_ACCOUNT_STATUS_CHANGE_HISTORY, accountStatusChangeHistoryMapper);
  }
}
