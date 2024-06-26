package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.unit.data.AccountDormancyControllerData.CLIENT_ID;

import com.kotak.ra.uams.entity.UserAccountStatusChangeHistory;
import com.kotak.ra.uams.model.AccountStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Account status history details service data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountStatusHistoryDetailsServiceData {

  /** The constant USER_ACCOUNT_STATUS_CHANGE_HISTORY. */
  public static final UserAccountStatusChangeHistory USER_ACCOUNT_STATUS_CHANGE_HISTORY =
      new UserAccountStatusChangeHistory(
          "KMBL123",
          "Abc",
          new StringBuilder()
              .append(CLIENT_ID)
              .append("#")
              .append(AccountStatus.ACTIVE.name())
              .toString(),
          AccountStatus.ACTIVE,
          1716364066L,
          "KMBL456",
          "KMBL456",
          "Approving the request.",
          CLIENT_ID);
}
