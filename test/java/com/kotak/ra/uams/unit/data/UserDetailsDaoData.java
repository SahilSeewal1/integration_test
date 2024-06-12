package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.DbConstants.ACCOUNT_DORMANCY_INDEX;
import static com.kotak.ra.uams.constant.DormancyConstant.KEY_CONDITION_EXPRESSION_MAP;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kotak.ra.uams.entity.AccountDetails;
import com.kotak.ra.uams.helper.DdbQueryHelper;
import com.kotak.ra.uams.model.AccountStatus;
import com.kotak.ra.uams.model.DbQueryRequest;
import com.kotak.ra.uams.model.DdbQueryRequest;
import com.kotak.ra.uams.model.LastKeyReferenceByAccountStatus;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type User details dao data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsDaoData {
  /** The Expression attribute value. */
  public static final Map<String, AttributeValue> EXPRESSION_ATTRIBUTE_VALUE =
      DdbQueryHelper.getExpressionAttributeValueByAccountStatus(
          AccountStatus.ACTIVE, UserAccessManagementServiceData.CLIENT_ID, 1016364066L, 1776364066L);

  /** The constant DDB_QUERY_REQUEST. */
  public static final DbQueryRequest DDB_QUERY_REQUEST =
      new DdbQueryRequest(
          KEY_CONDITION_EXPRESSION_MAP.get(AccountStatus.ACTIVE),
          EXPRESSION_ATTRIBUTE_VALUE,
          ACCOUNT_DORMANCY_INDEX,
          AccountDetails.class,
          5,
          AccountDormancyServiceData.LAST_REFERENCE_KEY_ACTIVE_ACCOUNT,
          LastKeyReferenceByAccountStatus.class);
}
