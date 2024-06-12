package com.kotak.ra.uams.unit.service;

import static com.kotak.ra.uams.constant.ResponseMessage.AWS_COGNITO_TOKEN_CREATION_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.DYNAMO_DB_GET_ITEM_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.DYNAMO_DB_SAVE_ITEM_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.KMS_DECRYPTION_FAILURE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ACCESS_TOKEN;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ACCESS_TOKEN_DETAILS;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.AUTHENTICATED_USERINFO;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.CLIENT_ID;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.DOMAIN;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.DORMANT_ACCOUNT_USER_DETAILS;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ENCRYPTED_DATA;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ID;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ID_TOKEN;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.ID_TOKEN_PAYLOAD;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.REFRESHED_TOKENS_DETAIL;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.REFRESH_TOKEN_RESPONSE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.RESPONSE_BODY;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKEN;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKENS_RESPONSE_BODY;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKEN_DETAIL;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKEN_REQUEST_OBJ;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKEN_RESPONSE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.USER_DETAILS_ACTIVE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.USER_DETAILS_INACTIVE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.USER_ID;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.USER_INFO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.converter.ObjectConverter;
import com.kotak.ra.uams.dao.AwsDynamoDbDao;
import com.kotak.ra.uams.entity.UserDetails;
import com.kotak.ra.uams.exception.UamsAuthServerException;
import com.kotak.ra.uams.exception.UamsAuthServerLogoutException;
import com.kotak.ra.uams.exception.UamsCryptographyException;
import com.kotak.ra.uams.exception.UamsDbException;
import com.kotak.ra.uams.helper.CookieHelper;
import com.kotak.ra.uams.helper.ObjectHelper;
import com.kotak.ra.uams.helper.TokenHelper;
import com.kotak.ra.uams.model.AccessTokenDetails;
import com.kotak.ra.uams.model.TokensDetail;
import com.kotak.ra.uams.model.UserInfo;
import com.kotak.ra.uams.sao.AuthenticationTokenSao;
import com.kotak.ra.uams.sao.KeyManagementSao;
import com.kotak.ra.uams.service.impl.UserAccessManagementServiceImpl;
import com.kotak.ra.uams.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;

/** The type User access management service test. */
@Log4j2
public class UserAccessManagementServiceTest {

  @Mock private AuthenticationTokenSao authenticationTokenSao;

  @Mock private AwsDynamoDbDao awsDynamoDbDao;

  @Mock private KeyManagementSao keyManagementSao;

  @Mock private ObjectConverter objectConverter;

  @Mock private TokenHelper tokenHelper;

  @Mock private CookieHelper cookieHelper;

  @Mock private UserDetailsServiceImpl userDetailsServiceImpl;

  @Mock private ObjectHelper objectHelper;

  @Mock private HttpServletResponse httpServletResponse;

  @MockBean(name = "userDetailsTable")
  private DynamoDBMapper userDetailsMapper;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** The User access management service. */
  @InjectMocks private UserAccessManagementServiceImpl userAccessManagementServiceImpl;

  /** Successfully fetch access token and user id. */
  @Test
  @DisplayName("Access token and userId successful fetch test.")
  public void successfullyFetchAccessTokenAndUserId() {

    when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
    when(awsDynamoDbDao.getItem(ID, UserDetails.class, userDetailsMapper))
        .thenReturn(USER_DETAILS_INACTIVE);
    doNothing().when(awsDynamoDbDao).setItem(USER_DETAILS_INACTIVE, userDetailsMapper);
    when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class)).thenReturn(TOKEN_DETAIL);
    when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
    when(tokenHelper.getJwtTokenInfo(ID_TOKEN, UserInfo.class)).thenReturn(USER_INFO);
    when(tokenHelper.getJwtTokenInfo(TOKEN_DETAIL.getAccessToken(), AccessTokenDetails.class))
        .thenReturn(ACCESS_TOKEN_DETAILS);
    when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);
    when(keyManagementSao.encryptData(any())).thenReturn(any());

    assertEquals(
        AUTHENTICATED_USERINFO,
        userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse));
  }

  /** Fetch access token and user id when session already exist. */
  @Test
  @DisplayName(
      "Fetch access token and userId successfully when previous session is already active.")
  public void fetchAccessTokenAndUserIdWhenSessionAlreadyExist() {
    when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
    when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class)).thenReturn(TOKEN_DETAIL);
    when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
    when(tokenHelper.getJwtTokenInfo(ID_TOKEN, UserInfo.class)).thenReturn(USER_INFO);
    when(tokenHelper.getJwtTokenInfo(TOKEN_DETAIL.getAccessToken(), AccessTokenDetails.class))
        .thenReturn(ACCESS_TOKEN_DETAILS);
    when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);
    when(userDetailsServiceImpl.getUserAccountDetails(CLIENT_ID, USER_ID, UserDetails.class))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(objectHelper.createUserDetails(any(), any(), any(), any(), any(), any(), any()))
        .thenReturn(USER_DETAILS_INACTIVE);
    doNothing().when(userDetailsServiceImpl).setUserAccountDetails(USER_DETAILS_INACTIVE);

    assertEquals(
        AUTHENTICATED_USERINFO,
        userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse));
  }

  /** Account found dormant. */
  @Test
  @DisplayName("Account dormancy validation test.")
  public void accountFoundDormant() {
    when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
    when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class)).thenReturn(TOKEN_DETAIL);
    when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
    when(tokenHelper.getJwtTokenInfo(ID_TOKEN, UserInfo.class)).thenReturn(USER_INFO);
    when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);
    when(userDetailsServiceImpl.getUserAccountDetails(
            TOKEN_REQUEST_OBJ.getClientId(), USER_ID, UserDetails.class))
        .thenReturn(DORMANT_ACCOUNT_USER_DETAILS);

    assertThrows(
        IllegalStateException.class,
        () -> {
          userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
        });
  }

  /** Cognito client exception login. */
  @Test
  @DisplayName("Error appear in aws cognito api failure scenario test.")
  public void cognitoClientExceptionLogin() {
    try {

      doThrow(new UamsAuthServerException())
          .when(authenticationTokenSao)
          .getTokens(TOKEN_REQUEST_OBJ);

      assertThrows(
          UamsAuthServerException.class,
          () -> {
            userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
          });
    } catch (Exception e) {
      log.error("Error occurred while executing test for cognito token revoke API: {}", e);
    }
  }

  /** Gets item dynamo db client exception login. */
  @Test
  @DisplayName("Test when error appear during aws dynamoDb get operation while login.")
  public void getItemDynamoDbClientExceptionLogin() {
    try {

      when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
      when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class))
          .thenReturn(TOKEN_DETAIL);
      when(tokenHelper.getJwtTokenInfo(ID_TOKEN, any())).thenReturn(USER_INFO);
      when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
      when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);

      doThrow(new UamsDbException())
          .when(userDetailsServiceImpl)
          .getUserAccountDetails(TOKEN_REQUEST_OBJ.getClientId(), USER_ID, UserDetails.class);

      assertThrows(
          UamsDbException.class,
          () -> {
            userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
          });
    } catch (Exception e) {
      log.error("Error occurred while executing test for dynamo db get item operation: {}", e);
    }
  }

  /** Save item dynamo db client exception login. */
  @Test
  @DisplayName("Test when error appear during aws dynamoDb put operation while login.")
  public void saveItemDynamoDbClientExceptionLogin() {
    try {

      when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
      when(awsDynamoDbDao.getItem(ID, UserDetails.class, userDetailsMapper))
          .thenReturn(USER_DETAILS_ACTIVE);
      when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class))
          .thenReturn(TOKEN_DETAIL);
      when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
      when(tokenHelper.getJwtTokenInfo(ID_TOKEN, UserInfo.class)).thenReturn(USER_INFO);
      when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);
      when(keyManagementSao.encryptData(any())).thenReturn(ENCRYPTED_DATA);

      doThrow(new UamsDbException()).when(userDetailsServiceImpl).setUserAccountDetails(any());

      assertThrows(
          UamsDbException.class,
          () -> {
            userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
          });
    } catch (Exception e) {
      log.error("Error occurred while executing test for dynamo db put item operation: {}", e);
    }
  }

  /** Encryption kms client exception login. */
  @Test
  @DisplayName("Test when error appear during kms data encryption operation while login.")
  public void encryptionKmsClientExceptionLogin() {
    try {

      when(authenticationTokenSao.getTokens(TOKEN_REQUEST_OBJ)).thenReturn(TOKEN_DETAIL);
      when(objectConverter.parse(TOKENS_RESPONSE_BODY, TokensDetail.class))
          .thenReturn(TOKEN_DETAIL);
      when(tokenHelper.getIdTokenPayload(ID_TOKEN)).thenReturn(ID_TOKEN_PAYLOAD);
      when(tokenHelper.getJwtTokenInfo(ID_TOKEN, UserInfo.class)).thenReturn(USER_INFO);
      when(objectConverter.parse(ID_TOKEN_PAYLOAD, UserInfo.class)).thenReturn(USER_INFO);

      when(keyManagementSao.encryptData(anyString())).thenThrow(new UamsCryptographyException());

      assertThrows(
          UamsCryptographyException.class,
          () -> {
            userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
          });
    } catch (Exception e) {
      log.error("Error occurred while executing test for kms data encryption operation: {}", e);
    }
  }

  /** Data parse exception login. */
  @Test
  @DisplayName("Test when error appear during data parse operation while login.")
  public void dataParseExceptionLogin() {
    doThrow(new ParseException()).when(authenticationTokenSao).getTokens(any());

    assertThrows(
        ParseException.class,
        () -> {
          userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJ, httpServletResponse);
        });
  }

  /** Successfully revoke tokens. */
  @Test
  @DisplayName("Successful logout test")
  public void successfullyRevokeTokens() {
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    doNothing().when(authenticationTokenSao).revokeTokens(any(), any(), any());
    doNothing().when(awsDynamoDbDao).setItem(any(), any());
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);

    userAccessManagementServiceImpl.revokeTokens(TOKEN, CLIENT_ID, DOMAIN, httpServletResponse);
    verify(userDetailsServiceImpl).setUserAccountDetails(any());
  }

  /** Cognito client exception revoke tokens. */
  @Test
  @DisplayName("Unsuccessful logout test when issue with aws cognito")
  public void cognitoClientExceptionRevokeTokens() {
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    doThrow(new UamsAuthServerException(AWS_COGNITO_TOKEN_CREATION_FAILURE))
        .when(authenticationTokenSao)
        .revokeTokens(any(), any(), any());
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);

    assertThrows(
        UamsAuthServerException.class,
        () -> {
          userAccessManagementServiceImpl.revokeTokens(
              TOKEN, CLIENT_ID, DOMAIN, httpServletResponse);
        });
  }

  /** Gets item dynamo db client exception revoke tokens. */
  @Test
  @DisplayName("Unsuccessful logout test when issue with dynamo db get operation")
  public void getItemDynamoDbClientExceptionRevokeTokens() {
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);

    doThrow(new UamsDbException(DYNAMO_DB_GET_ITEM_FAILURE))
        .when(userDetailsServiceImpl)
        .getUserAccountDetails(any(), any(), any());

    assertThrows(
        UamsDbException.class,
        () -> {
          userAccessManagementServiceImpl.revokeTokens(
              TOKEN, CLIENT_ID, DOMAIN, httpServletResponse);
        });
  }

  /** Save item dynamo db client exception revoke tokens. */
  @Test
  @DisplayName("Unsuccessful logout test when issue with dynamo db put operation")
  public void saveItemDynamoDbClientExceptionRevokeTokens() {
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    doNothing().when(authenticationTokenSao).revokeTokens(any(), any(), any());

    doThrow(new UamsDbException(DYNAMO_DB_SAVE_ITEM_FAILURE))
        .when(userDetailsServiceImpl)
        .setUserAccountDetails(any());

    assertThrows(
        UamsDbException.class,
        () -> {
          userAccessManagementServiceImpl.revokeTokens(
              TOKEN, CLIENT_ID, DOMAIN, httpServletResponse);
        });
  }

  /** Decryption kms client exception revoke tokens. */
  @Test
  @DisplayName("Unsuccessful logout test when issue with kms decryption operation")
  public void decryptionKmsClientExceptionRevokeTokens() {
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);

    doThrow(new UamsCryptographyException(KMS_DECRYPTION_FAILURE))
        .when(keyManagementSao)
        .decryptData(any());

    assertThrows(
        UamsCryptographyException.class,
        () -> {
          userAccessManagementServiceImpl.revokeTokens(
              TOKEN, CLIENT_ID, DOMAIN, httpServletResponse);
        });
  }

  /** Input mismatch exception tokens. */
  @Test
  @DisplayName("Unsuccessful logout test when access token is invalid")
  public void inputMismatchExceptionTokens() {
    when(tokenHelper.getAccessTokenAndUserId(any())).thenReturn(TOKEN_RESPONSE);
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any())).thenReturn(null);

    assertEquals(
        false,
        userAccessManagementServiceImpl.revokeTokens(
            TOKEN, CLIENT_ID, DOMAIN, httpServletResponse));
  }

  /** Successfully refresh access token. */
  @Test
  @DisplayName("Successfully refresh Access Token Test")
  public void successfullyRefreshAccessToken() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_INACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(authenticationTokenSao.getRefreshedTokens(any(), any(), any())).thenReturn(RESPONSE_BODY);
    when(keyManagementSao.encryptData(any())).thenReturn(ENCRYPTED_DATA);
    doNothing().when(userDetailsServiceImpl).setUserAccountDetails(any());
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);
    when(objectConverter.parse(any(), any())).thenReturn(REFRESHED_TOKENS_DETAIL);

    assertEquals(
        REFRESH_TOKEN_RESPONSE,
        userAccessManagementServiceImpl.getRefreshedAccessToken(
            TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Refresh token expired test. */
  @Test
  @DisplayName("Refresh token already expired test")
  public void refreshTokenExpiredTest() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_INACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);
    doNothing().when(cookieHelper).setCookieInResponse(anyString(), any(), anyInt(), any());

    doThrow(new UamsAuthServerLogoutException())
        .when(authenticationTokenSao)
        .getRefreshedTokens(any(), any(), any());

    assertThrows(
        UamsAuthServerLogoutException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Refresh token api error test. */
  @Test
  @DisplayName("Refresh token API error test")
  public void refreshTokenApiErrorTest() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_INACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);
    doThrow(new UamsAuthServerException())
        .when(authenticationTokenSao)
        .getRefreshedTokens(any(), any(), any());

    assertThrows(
        UamsAuthServerException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Gets user details error. */
  @Test
  @DisplayName(
      "Error occurred while fetching user details from db while refresh access token api is invoked")
  public void getUserDetailsError() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);

    doThrow(new UamsDbException())
        .when(userDetailsServiceImpl)
        .getUserAccountDetails(any(), any(), any());

    assertThrows(
        UamsDbException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Save user details error. */
  @Test
  @DisplayName("Error occurred while saving data to database")
  public void saveUserDetailsError() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(authenticationTokenSao.getRefreshedTokens(any(), any(), any())).thenReturn(RESPONSE_BODY);
    when(keyManagementSao.encryptData(any())).thenReturn(ENCRYPTED_DATA);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);
    when(objectConverter.parse(any(), any())).thenReturn(REFRESHED_TOKENS_DETAIL);

    doThrow(new UamsDbException()).when(userDetailsServiceImpl).setUserAccountDetails(any());

    assertThrows(
        UamsDbException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Encrypt user data refresh test. */
  @Test
  @DisplayName(
      "Error occurred while encrypting data during refreshing access token api invocation.")
  public void encryptUserDataRefreshTest() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(authenticationTokenSao.getRefreshedTokens(any(), any(), any())).thenReturn(RESPONSE_BODY);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);
    when(objectConverter.parse(any(), any())).thenReturn(REFRESHED_TOKENS_DETAIL);

    doThrow(new UamsCryptographyException()).when(keyManagementSao).encryptData(any());

    assertThrows(
        UamsCryptographyException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Decrypt user data refresh test. */
  @Test
  @DisplayName(
      "Error occurred while decrypting data during refreshing access token api invocation.")
  public void decryptUserDataRefreshTest() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);

    doThrow(new UamsCryptographyException()).when(keyManagementSao).decryptData(any());

    assertThrows(
        UamsCryptographyException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }

  /** Parse user data refresh test. */
  @Test
  @DisplayName("Error occurred while parsing data during refreshing access token.")
  public void parseUserDataRefreshTest() {
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    when(userDetailsServiceImpl.getUserAccountDetails(any(), any(), any()))
        .thenReturn(USER_DETAILS_ACTIVE);
    when(keyManagementSao.decryptData(any())).thenReturn(ACCESS_TOKEN);
    when(authenticationTokenSao.getRefreshedTokens(any(), any(), any())).thenReturn(RESPONSE_BODY);
    when(tokenHelper.getAccessTokenAndUserId(TOKEN)).thenReturn(TOKEN_RESPONSE);

    doThrow(new ParseException()).when(objectConverter).parse(any(), any());

    assertThrows(
        ParseException.class,
        () ->
            userAccessManagementServiceImpl.getRefreshedAccessToken(
                TOKEN, DOMAIN, CLIENT_ID, httpServletResponse));
  }
}
