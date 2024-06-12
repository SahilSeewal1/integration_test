package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.DbConstants.ID_DELIMITER;

import com.kotak.ra.uams.constant.SessionStatus;
import com.kotak.ra.uams.entity.UserDetails;
import com.kotak.ra.uams.model.AccountStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Aws dynamo db dao data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AwsDynamoDbDaoData {
  /** The constant VALID_USER_DETAILS. */
  public static final UserDetails VALID_USER_DETAILS =
      new UserDetails(
          "KMBL123",
          "19kungvqs1dmi2q335nfjgta9m",
          "KMBL123",
          "abc",
          123456789L,
          "ASDFGHJKLZXCVBNMDFGHJK",
          "ASDFGHJKLQWERTYUIOPP",
          "ASDFGHJKLQWERTYUIOPBNM",
          2345678L,
          4567890L,
          SessionStatus.INACTIVE,
          "19kungvqs1dmi2q335nfjgta9m#ACTIVE",
          AccountStatus.ACTIVE,
          "false");

  /** The constant INVALID_USER_DETAILS. */
  public static final UserDetails INVALID_USER_DETAILS = new UserDetails();

  /** The constant ID. */
  public static final String ID = "12345sdfggh" + ID_DELIMITER + "KMBL123";
}
