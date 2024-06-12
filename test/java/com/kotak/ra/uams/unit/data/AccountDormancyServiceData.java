package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS;
import static com.kotak.ra.uams.constant.ResponseMessage.UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS;
import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.CLIENT_ID;

import com.kotak.ra.uams.entity.AccountDetails;
import com.kotak.ra.uams.model.AccountDetailsResponse;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.LastKeyReferenceByAccountStatus;
import com.kotak.ra.uams.model.PaginatedItems;
import com.kotak.ra.uams.model.UpdateUserAccountStatusResponse;
import com.kotak.ra.uams.model.UserAccountDetailsByStatusResponse;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Account dormancy service data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDormancyServiceData {
  /** The constant ACTIVE_ACCOUNT_DETAILS. */
  public static final List<AccountDetails> ACTIVE_ACCOUNT_DETAILS =
      Arrays.asList(
          new AccountDetails()
              .toBuilder()
                  .accountStatus(AccountStatus.ACTIVE)
                  .id(CLIENT_ID + "#" + "KMBL123")
                  .name("Abc")
                  .userId("KMBL123")
                  .lastLoginTime(1516364066L)
                  .clientIdAccountStatus(CLIENT_ID + "#" + AccountStatus.ACTIVE.name())
                  .accountStatusUpdationTime(1316364066L)
                  .build(),
          new AccountDetails()
              .toBuilder()
                  .accountStatus(AccountStatus.ACTIVE)
                  .id(CLIENT_ID + "#" + "KMBL890")
                  .name("Xyz")
                  .userId("KMBL890")
                  .lastLoginTime(1616364066L)
                  .clientIdAccountStatus(CLIENT_ID + "#" + AccountStatus.ACTIVE.name())
                  .accountStatusUpdationTime(1416364066L)
                  .build());

  /** The constant LAST_REFERENCE_KEY_ACTIVE_ACCOUNT. */
  public static final LastKeyReferenceByAccountStatus LAST_REFERENCE_KEY_ACTIVE_ACCOUNT =
      new LastKeyReferenceByAccountStatus()
          .accountStatus(AccountStatus.ACTIVE)
          .userId("KMBL890")
          .lastLoginTime(1616364066L)
          .clientId(CLIENT_ID);

  /** The constant PAGINATED_ITEMS_ACTIVE_ACCOUNT. */
  public static final PaginatedItems<AccountDetails, LastKeyReferenceByAccountStatus>
      PAGINATED_ITEMS_ACTIVE_ACCOUNT =
          new PaginatedItems(ACTIVE_ACCOUNT_DETAILS, LAST_REFERENCE_KEY_ACTIVE_ACCOUNT);

  /** The constant ACTIVE_ACCOUNT_DETAILS_RESPONSE_LIST. */
  public static final List<AccountDetailsResponse> ACTIVE_ACCOUNT_DETAILS_RESPONSE_LIST =
      Arrays.asList(
          new AccountDetailsResponse()
              .accountStatus(ACTIVE_ACCOUNT_DETAILS.get(0).getAccountStatus())
              .name(ACTIVE_ACCOUNT_DETAILS.get(0).getName())
              .userId(ACTIVE_ACCOUNT_DETAILS.get(0).getUserId())
              .statusUpdationTime(ACTIVE_ACCOUNT_DETAILS.get(0).getAccountStatusUpdationTime()),
          new AccountDetailsResponse()
              .accountStatus(ACTIVE_ACCOUNT_DETAILS.get(1).getAccountStatus())
              .name(ACTIVE_ACCOUNT_DETAILS.get(1).getName())
              .userId(ACTIVE_ACCOUNT_DETAILS.get(1).getUserId())
              .statusUpdationTime(ACTIVE_ACCOUNT_DETAILS.get(1).getAccountStatusUpdationTime()));

  /** The constant USER_ACCOUNT_DETAILS_BY_ACTIVE_STATUS_RESPONSE. */
  public static final UserAccountDetailsByStatusResponse USER_ACCOUNT_DETAILS_BY_ACTIVE_STATUS_RESPONSE =
      new UserAccountDetailsByStatusResponse(
          CLIENT_ID, ACTIVE_ACCOUNT_DETAILS_RESPONSE_LIST, LAST_REFERENCE_KEY_ACTIVE_ACCOUNT);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_RESPONSE_ACTIVE. */
  public static final UpdateUserAccountStatusResponse UPDATE_USER_ACCOUNT_STATUS_RESPONSE_ACTIVE =
      new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_ACTIVE_SUCCESS);

  /** The constant UPDATE_USER_ACCOUNT_STATUS_RESPONSE_DORMANT. */
  public static final UpdateUserAccountStatusResponse UPDATE_USER_ACCOUNT_STATUS_RESPONSE_DORMANT =
      new UpdateUserAccountStatusResponse().message(UPDATE_ACCOUNT_STATUS_AS_DORMANT_SUCCESS);

  /** The constant DORMANT_ACCOUNT_DETAILS. */
  public static final List<AccountDetails> DORMANT_ACCOUNT_DETAILS =
      Arrays.asList(
          new AccountDetails()
              .toBuilder()
                  .accountStatus(AccountStatus.DORMANT)
                  .id(CLIENT_ID + "#" + "KMBL123")
                  .name("Abc")
                  .userId("KMBL123")
                  .lastLoginTime(1516364066L)
                  .clientIdAccountStatus(CLIENT_ID + "#" + AccountStatus.ACTIVE.name())
                  .accountStatusUpdationTime(1316364066L)
                  .build(),
          new AccountDetails()
              .toBuilder()
                  .accountStatus(AccountStatus.DORMANT)
                  .id(CLIENT_ID + "#" + "KMBL890")
                  .name("Xyz")
                  .userId("KMBL890")
                  .lastLoginTime(1616364066L)
                  .clientIdAccountStatus(CLIENT_ID + "#" + AccountStatus.ACTIVE.name())
                  .accountStatusUpdationTime(1416364066L)
                  .build());

  /** The constant PAGINATED_ITEMS_DORMANT_ACCOUNT. */
  public static final PaginatedItems<AccountDetails, LastKeyReferenceByAccountStatus>
      PAGINATED_ITEMS_DORMANT_ACCOUNT =
          new PaginatedItems(DORMANT_ACCOUNT_DETAILS, LAST_REFERENCE_KEY_ACTIVE_ACCOUNT);

  /** The constant USER_ACCOUNT_DETAIL. */
  public static final AccountDetails USER_ACCOUNT_DETAIL =
      new AccountDetails()
          .toBuilder()
              .accountStatus(AccountStatus.ACTIVE)
              .id(CLIENT_ID + "#" + "KMBL123")
              .name("Abc")
              .userId("KMBL123")
              .lastLoginTime(1516364066L)
              .clientIdAccountStatus(CLIENT_ID + "#" + AccountStatus.ACTIVE.name())
              .accountStatusUpdationTime(1316364066L)
              .build();
}
