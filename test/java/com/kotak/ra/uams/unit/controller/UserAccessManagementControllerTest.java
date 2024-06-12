package com.kotak.ra.uams.unit.controller;

import static com.kotak.ra.uams.constant.CookieGeneratorConstants.COOKIE_MAX_AGE;
import static com.kotak.ra.uams.constant.CookieGeneratorConstants.COOKIE_MIN_AGE;
import static com.kotak.ra.uams.constant.CookieGeneratorConstants.COOKIE_SUFFIX;
import static com.kotak.ra.uams.constant.ResponseMessage.AWS_COGNITO_TOKEN_REVOKE_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.DYNAMO_DB_GET_ITEM_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.DYNAMO_DB_SAVE_ITEM_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.KMS_DECRYPTION_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.KMS_ENCRYPTION_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.LOGOUT_FLOW_SUCCESS;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.COOKIE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.LOGOUT_REQUEST;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.REFRESH_REQUEST;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.REQUEST_ID;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.SUCCESSFUL_REFRESH_RESPONSE;
import static com.kotak.ra.uams.unit.data.UserAccessManagementControllerData.TOKEN_REQUEST_OBJECT;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.AUTHENTICATED_USERINFO;
import static com.kotak.ra.uams.unit.data.UserAccessManagementServiceData.TOKEN_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.kotak.ra.uams.constant.ResponseMessage;
import com.kotak.ra.uams.controller.UserAccessManagementController;
import com.kotak.ra.uams.exception.UamsAuthServerException;
import com.kotak.ra.uams.exception.UamsAuthServerLogoutException;
import com.kotak.ra.uams.exception.UamsCryptographyException;
import com.kotak.ra.uams.exception.UamsDbException;
import com.kotak.ra.uams.helper.CookieHelper;
import com.kotak.ra.uams.model.CreateTokenResponse;
import com.kotak.ra.uams.model.LogoutResponse;
import com.kotak.ra.uams.service.impl.UserAccessManagementServiceImpl;
import com.kotak.ra.uams.unit.data.UserAccessManagementServiceData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/** The type User access management controller test. */
@Log4j2
public class UserAccessManagementControllerTest {

  /** The User access management service. */
  @Mock private UserAccessManagementServiceImpl userAccessManagementServiceImpl;

  /** The Cookie helper. */
  @Mock private CookieHelper cookieHelper;

  /** The Http servlet response. */
  @Mock private HttpServletResponse httpServletResponse;

  /** The User access management controller. */
  @InjectMocks private UserAccessManagementController userAccessManagementController;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Successful login test. */
  @Test
  @DisplayName("Test in case of successful login.")
  public void successfulLoginTest() {

    when(userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse))
            .thenReturn(AUTHENTICATED_USERINFO);

    doNothing().when(httpServletResponse).addCookie(COOKIE);

    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK)
            .body(
                new CreateTokenResponse(
                        ResponseMessage.LOGIN_FLOW_SUCCESS,
                        AUTHENTICATED_USERINFO.getUserId(),
                        AUTHENTICATED_USERINFO.getUserName(),
                        AUTHENTICATED_USERINFO.getEmail())
                    .roles(AUTHENTICATED_USERINFO.getRoles()));

    assertEquals(expected, userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT));
  }

  /** Successful login when session already exist test. */
  @Test
  @DisplayName("Test in case of successful login when previous session already exist.")
  public void successfulLoginWhenSessionAlreadyExistTest() {

    when(userAccessManagementServiceImpl.getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse))
            .thenReturn(AUTHENTICATED_USERINFO);

    when(cookieHelper.builder(any(), any()))
            .thenReturn(
                    new CookieHelper.CookieHelperBuilder(
                            TOKEN_REQUEST_OBJECT.getClientId() + COOKIE_SUFFIX,
                            UserAccessManagementServiceData.TOKEN));

    ResponseEntity expected =
        ResponseEntity.status(HttpStatus.OK)
            .body(
                new CreateTokenResponse(
                        ResponseMessage.LOGIN_FLOW_SUCCESS,
                        AUTHENTICATED_USERINFO.getUserId(),
                        AUTHENTICATED_USERINFO.getUserName(),
                        AUTHENTICATED_USERINFO.getEmail())
                    .roles(AUTHENTICATED_USERINFO.getRoles()));

    assertEquals(expected, userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT));
  }

  /**
   * Cognito client exception login test.
   */
  @Test
  @DisplayName("Test in case of unsuccessful login when issue with aws cognito.")
  public void cognitoClientExceptionLoginTest() {
    doThrow(new UamsAuthServerException())
            .when(userAccessManagementServiceImpl)
            .getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse);
    assertThrows(
            UamsAuthServerException.class,
            () -> {
              userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT);
            });
  }

  /**
   * Account dormant login test.
   */
  @Test
  @DisplayName("Test in case of unsuccessful login when account is dormant.")
  public void accountDormantLoginTest() {
    doThrow(new IllegalStateException())
            .when(userAccessManagementServiceImpl)
            .getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse);
    assertThrows(
            IllegalStateException.class,
            () -> {
              userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT);
            });
  }

  /**
   * Save item dynamo db client exception login test.
   */
  @Test
  @DisplayName("Test in case of unsuccessful login when issue with dynamoDb put operation.")
  public void saveItemDynamoDbClientExceptionLoginTest() {
    doThrow(new UamsDbException())
            .when(userAccessManagementServiceImpl)
            .getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse);
    assertThrows(
            UamsDbException.class,
            () -> {
              userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT);
            });
  }

  /**
   * Encryption kms client exception login test.
   */
  @Test
  @DisplayName("Test in case of unsuccessful login when issue with kms encrypt operation.")
  public void encryptionKmsClientExceptionLoginTest() {
    doThrow(new UamsCryptographyException())
            .when(userAccessManagementServiceImpl)
            .getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse);
    assertThrows(
            UamsCryptographyException.class,
            () -> {
              userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT);
            });
  }

  /**
   * Parse data exception login test.
   */
  @Test
  @DisplayName("Test in case of unsuccessful login when issue with data parse operation.")
  public void parseDataExceptionLoginTest() {
    doThrow(new ParseException())
            .when(userAccessManagementServiceImpl)
            .getTokens(TOKEN_REQUEST_OBJECT, httpServletResponse);
    assertThrows(
            ParseException.class,
            () -> {
              userAccessManagementController._getTokens(REQUEST_ID, TOKEN_REQUEST_OBJECT);
            });
  }

  /** Successful logout test. */
  @Test
  @DisplayName("Test in case of successful logout.")
  public void successfulLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);
    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);
    doNothing()
            .when(cookieHelper)
            .setCookieInResponse(
                    LOGOUT_REQUEST.getClientId(), null, COOKIE_MIN_AGE, httpServletResponse);
    when(userAccessManagementServiceImpl.revokeTokens(
                            anyString(), anyString(), anyString(), any()))
            .thenReturn(true);
    assertEquals(
            ResponseEntity.status(HttpStatus.OK).body(new LogoutResponse(LOGOUT_FLOW_SUCCESS)),
            userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST));
  }

  /** Invalid cookie logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout.when empty/invalid cookie is provided")
  public void invalidCookieLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    userAccessManagementController.setHttpServletRequest(request);

    doThrow(new IllegalArgumentException())
            .when(cookieHelper)
            .getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request);

    assertThrows(
            IllegalArgumentException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Save item dynamo db client exception logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout when issue with dynamoDb put operation")
  public void saveItemDynamoDbClientExceptionLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsDbException(DYNAMO_DB_SAVE_ITEM_FAILURE))
            .when(userAccessManagementServiceImpl)
            .revokeTokens(any(), any(), any(), any());

    assertThrows(
            UamsDbException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Gets item dynamo db client exception logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout when issue with dynamoDb get operation")
  public void getItemDynamoDbClientExceptionLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsDbException(DYNAMO_DB_GET_ITEM_FAILURE))
            .when(userAccessManagementServiceImpl)
            .revokeTokens(any(), any(), any(), any());

    assertThrows(
            UamsDbException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Encryption kms client exception logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout when issue with kms encryption operation")
  public void encryptionKmsClientExceptionLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsCryptographyException(KMS_ENCRYPTION_FAILURE))
            .when(userAccessManagementServiceImpl)
            .revokeTokens(any(), any(), any(), any());

    assertThrows(
            UamsCryptographyException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Decryption kms client exception logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout when issue with kms decryption operation")
  public void decryptionKmsClientExceptionLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsCryptographyException(KMS_DECRYPTION_FAILURE))
            .when(userAccessManagementServiceImpl)
            .revokeTokens(any(), any(), any(), any());

    assertThrows(
            UamsCryptographyException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Cognito client exception logout test. */
  @Test
  @DisplayName("Test in case of unsuccessful logout when issue with aws cognito")
  public void cognitoClientExceptionLogoutTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(LOGOUT_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsAuthServerException(AWS_COGNITO_TOKEN_REVOKE_FAILURE))
            .when(userAccessManagementServiceImpl)
            .revokeTokens(any(), any(), any(), any());

    assertThrows(
            UamsAuthServerException.class,
            () -> {
              userAccessManagementController._revokeTokens(REQUEST_ID, LOGOUT_REQUEST);
            });
  }

  /** Successfully refresh access token test. */
  @Test
  @DisplayName("Successfully refresh Access Token Test")
  public void successfullyRefreshAccessTokenTest() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);
    userAccessManagementController.setHttpServletResponse(response);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);
    doNothing()
            .when(cookieHelper)
            .setCookieInResponse(
                    REFRESH_REQUEST.getClientId(),
                    COOKIE.getValue(),
                    COOKIE_MAX_AGE,
                    httpServletResponse);
    when(userAccessManagementServiceImpl.getRefreshedAccessToken(any(), any(), any(), any()))
            .thenReturn(TOKEN_RESPONSE);

    assertEquals(
            SUCCESSFUL_REFRESH_RESPONSE,
            userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST));
  }

  /** Invalid cookie exception test. */
  @Test
  @DisplayName("Invalid cookie exception while refreshing access token test")
  public void invalidCookieExceptionTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    userAccessManagementController.setHttpServletRequest(request);

    doThrow(new IllegalArgumentException())
            .when(cookieHelper)
            .getCookieFromRequest(REFRESH_REQUEST.getClientId(), request);

    assertThrows(
            IllegalArgumentException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }

  /** Refresh token expired exception test. */
  @Test
  @DisplayName("Refresh token expired exception")
  public void refreshTokenExpiredExceptionTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsAuthServerLogoutException())
            .when(userAccessManagementServiceImpl)
            .getRefreshedAccessToken(any(), any(), any(), any());

    assertThrows(
            UamsAuthServerLogoutException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }

  /** Refresh token api exception test. */
  @Test
  @DisplayName("Cognito refresh token API exception")
  public void refreshTokenApiExceptionTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsAuthServerException())
            .when(userAccessManagementServiceImpl)
            .getRefreshedAccessToken(any(), any(), any(), any());

    assertThrows(
            UamsAuthServerException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }

  /** Save or read data exception test. */
  @Test
  @DisplayName("Exception while saving/reading data in db during access token refresh")
  public void saveOrReadDataExceptionTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsDbException())
            .when(userAccessManagementServiceImpl)
            .getRefreshedAccessToken(any(), any(), any(), any());

    assertThrows(
            UamsDbException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }

  /** Encrypt or decrypt data exception test. */
  @Test
  @DisplayName("Exception while encrypting/decrypting data during access token refresh")
  public void encryptOrDecryptDataExceptionTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new UamsCryptographyException())
            .when(userAccessManagementServiceImpl)
            .getRefreshedAccessToken(any(), any(), any(), any());

    assertThrows(
            UamsCryptographyException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }

  /** Parse data exception refresh test. */
  @Test
  @DisplayName("Exception while parsing data during access token refresh")
  public void parseDataExceptionRefreshTest() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(COOKIE);
    userAccessManagementController.setHttpServletRequest(request);

    when(cookieHelper.getCookieFromRequest(REFRESH_REQUEST.getClientId(), request))
            .thenReturn(COOKIE);

    doThrow(new ParseException())
            .when(userAccessManagementServiceImpl)
            .getRefreshedAccessToken(any(), any(), any(), any());

    assertThrows(
            ParseException.class,
            () -> {
              userAccessManagementController._getRefreshedToken(REQUEST_ID, REFRESH_REQUEST);
            });
  }
}
