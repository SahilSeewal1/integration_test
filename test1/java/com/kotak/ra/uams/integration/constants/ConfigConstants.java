package com.kotak.ra.uams.integration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/** The type Config constants. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigConstants {

  /** The constant USER_DETAILS_TEST_BEAN. */
  public static final String USER_DETAILS_TEST_BEAN = "userDetailsTableTest";

  /** The constant ACCOUNT_STATUS_CHANGE_HISTORY_TEST_BEAN. */
  public static final String ACCOUNT_STATUS_CHANGE_HISTORY_TEST_BEAN =
      "accountStatusChangeHistoryTableTest";
}
