package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_ACTIVE_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS;

import com.kotak.ra.uams.model.AccountDetailsResponse;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.LastKeyReferenceByAccountStatus;
import com.kotak.ra.uams.model.UpdateUserAccountStatusRequest;
import com.kotak.ra.uams.model.UpdateUserAccountStatusResponse;
import com.kotak.ra.uams.model.UserAccountDetailsByStatusRequest;
import com.kotak.ra.uams.model.UserAccountDetailsByStatusResponse;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Account dormancy controller data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDormancyControllerData {
  /** The constant CLIENT_ID. */
  public static final String CLIENT_ID = "2bj1234567890";

  /** The constant START_EPOCH_TIME. */
  public static final Long START_EPOCH_TIME = 1516364066L;

  /** The constant END_EPOCH_TIME. */
  public static final Long END_EPOCH_TIME = 1716364066L;

  /** The constant ACTIVE_USER_ACCOUNT_REQUEST. */
  public static final UserAccountDetailsByStatusRequest ACTIVE_USER_ACCOUNT_REQUEST =
      new UserAccountDetailsByStatusRequest()
          .accountStatus(AccountStatus.ACTIVE)
          .clientId(CLIENT_ID)
          .lastKeyReference(null)
          .pageSize(2)
          .lastLoginStartTime(START_EPOCH_TIME)
          .lastLoginEndTime(END_EPOCH_TIME);

  /** The constant ACTIVE_ACCOUNT_DETAILS. */
  public static final List<AccountDetailsResponse> ACTIVE_ACCOUNT_DETAILS =
      Arrays.asList(
          new AccountDetailsResponse()
              .accountStatus(AccountStatus.ACTIVE)
              .name("Abc")
              .statusUpdationTime(null)
              .userId("KMBL123"),
          new AccountDetailsResponse()
              .accountStatus(AccountStatus.ACTIVE)
              .name("Xyz")
              .statusUpdationTime(null)
              .userId("KMBL890"));

  /** The constant DORMANT_ACCOUNT_DETAILS. */
  public static final List<AccountDetailsResponse> DORMANT_ACCOUNT_DETAILS =
      Arrays.asList(
          new AccountDetailsResponse()
              .accountStatus(AccountStatus.DORMANT)
              .name("Abc")
              .statusUpdationTime(null)
              .userId("KMBL123"),
          new AccountDetailsResponse()
              .accountStatus(AccountStatus.DORMANT)
              .name("Xyz")
              .statusUpdationTime(null)
              .userId("KMBL890"));

  /** The constant LAST_REFERENCE_KEY_BY_ACTIVE_ACCOUNT_STATUS. */
  public static final LastKeyReferenceByAccountStatus LAST_REFERENCE_KEY_BY_ACTIVE_ACCOUNT_STATUS =
      new LastKeyReferenceByAccountStatus()
          .accountStatus(AccountStatus.ACTIVE)
          .lastLoginTime(END_EPOCH_TIME)
          .userId("KMBL678")
          .clientId(CLIENT_ID);

  /** The constant ACTIVE_USER_ACCOUNT_RESPONSE. */
  public static final UserAccountDetailsByStatusResponse ACTIVE_USER_ACCOUNT_RESPONSE =
      new UserAccountDetailsByStatusResponse()
          .accountsDetail(ACTIVE_ACCOUNT_DETAILS)
          .clientId(CLIENT_ID)
          .lastKeyReference(LAST_REFERENCE_KEY_BY_ACTIVE_ACCOUNT_STATUS);

  /** The constant DORMANT_USER_ACCOUNT_REQUEST. */
  public static final UserAccountDetailsByStatusRequest DORMANT_USER_ACCOUNT_REQUEST =
      new UserAccountDetailsByStatusRequest()
          .accountStatus(AccountStatus.DORMANT)
          .clientId(CLIENT_ID)
          .lastKeyReference(null)
          .pageSize(2)
          .lastLoginStartTime(START_EPOCH_TIME)
          .lastLoginEndTime(END_EPOCH_TIME);

  /** The constant LAST_REFERENCE_KEY_BY_DORMANT_ACCOUNT_STATUS. */
  public static final LastKeyReferenceByAccountStatus LAST_REFERENCE_KEY_BY_DORMANT_ACCOUNT_STATUS =
      new LastKeyReferenceByAccountStatus()
          .accountStatus(AccountStatus.DORMANT)
          .lastLoginTime(END_EPOCH_TIME)
          .userId("KMBL678")
          .clientId(CLIENT_ID);

  /** The constant DORMANT_USER_ACCOUNT_RESPONSE. */
  public static final UserAccountDetailsByStatusResponse DORMANT_USER_ACCOUNT_RESPONSE =
      new UserAccountDetailsByStatusResponse()
          .accountsDetail(DORMANT_ACCOUNT_DETAILS)
          .clientId(CLIENT_ID)
          .lastKeyReference(LAST_REFERENCE_KEY_BY_DORMANT_ACCOUNT_STATUS);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST. */
  public static final UpdateUserAccountStatusRequest UPDATE_USER_ACCOUNT_STATUS_ACTIVE_REQUEST =
      new UpdateUserAccountStatusRequest()
          .accountStatus(AccountStatus.ACTIVE)
          .userId("KMBL123")
          .clientId(CLIENT_ID)
          .approverId("KMBL456")
          .remark("Activating account");

  /** The constant UPDATE_USER_ACCOUNT_STATUS_ACTIVE_RESPONSE. */
  public static final UpdateUserAccountStatusResponse UPDATE_USER_ACCOUNT_STATUS_ACTIVE_RESPONSE =
      new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST. */
  public static final UpdateUserAccountStatusRequest UPDATE_USER_ACCOUNT_STATUS_DORMANT_REQUEST =
      new UpdateUserAccountStatusRequest()
          .accountStatus(AccountStatus.DORMANT)
          .userId("KMBL123")
          .clientId(CLIENT_ID);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_DORMANT_RESPONSE. */
  public static final UpdateUserAccountStatusResponse UPDATE_USER_ACCOUNT_STATUS_DORMANT_RESPONSE =
      new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_ALREADY_ACTIVE_RESPONSE. */
  public static final UpdateUserAccountStatusResponse UPDATE_USER_ACCOUNT_STATUS_ALREADY_ACTIVE_RESPONSE =
      new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_ACTIVE_FAILURE);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_ALREADY_DORMANT_RESPONSE. */
  public static final UpdateUserAccountStatusResponse
      UPDATE_USER_ACCOUNT_STATUS_ALREADY_DORMANT_RESPONSE =
          new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_DORMANT_FAILURE);
}
