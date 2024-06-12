package com.kotak.ra.uams.unit.interceptor;

import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.AUTHORIZATION;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.EXPIRED_JWT;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.INVALID_JWT;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.REQUEST_ID_KEY;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.REQUEST_ID_VALUE;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.SECRET_KEY;
import static com.kotak.ra.uams.unit.data.CrmsAuthenticationInterceptorData.VALID_JWT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kotak.ra.uams.interceptor.CrmsAuthenticationInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/** The type Crms authentication interceptor test. */
public class CrmsAuthenticationInterceptorTest {
  /** The Crms authentication interceptor. */
  private CrmsAuthenticationInterceptor crmsAuthenticationInterceptor;

  /** Sets . */
  @BeforeEach
  public void setup() {
    this.crmsAuthenticationInterceptor = new CrmsAuthenticationInterceptor(SECRET_KEY);
  }

  /** Invalidate jwt token test. */
  @Test
  @DisplayName("Invalidate JWT token test.")
  public void invalidateJwtTokenTest() {

    MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    httpServletRequest.addHeader(AUTHORIZATION, INVALID_JWT);
    httpServletRequest.addHeader(REQUEST_ID_KEY, REQUEST_ID_VALUE);

    assertThrows(
        JWTVerificationException.class,
        () -> {
          crmsAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        });
  }

  /** No jwt token found test. */
  @Test
  @DisplayName("JWT token not found test.")
  public void noJwtTokenFoundTest() {

    MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    httpServletRequest.addHeader(REQUEST_ID_KEY, REQUEST_ID_VALUE);

    assertThrows(
            IllegalArgumentException.class,
        () -> {
          crmsAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        });
  }

  /** No jwt token found test. */
  @Test
  @DisplayName("JWT token expired test.")
  public void expiredJwtTokenFoundTest() {

    MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    httpServletRequest.addHeader(REQUEST_ID_KEY, REQUEST_ID_VALUE);
    httpServletRequest.addHeader(AUTHORIZATION, EXPIRED_JWT);

    assertThrows(
            JWTVerificationException.class,
            () -> {
              crmsAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
            });
  }

  /** No request id found test. */
  @Test
  @DisplayName("Request Id not found test.")
  public void noRequestIdFoundTest() {

    MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    httpServletRequest.addHeader(AUTHORIZATION, INVALID_JWT);

    assertThrows(
            IllegalArgumentException.class,
        () -> {
          crmsAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        });
  }

  /**
   * Validate jwt token test.
   *
   * @throws Exception the exception
   */
  @Test
  @DisplayName("Validate JWT token test.")
  public void validateJWTTokenTest() throws Exception {

    MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
    httpServletRequest.addHeader(AUTHORIZATION, VALID_JWT);
    httpServletRequest.addHeader(REQUEST_ID_KEY, REQUEST_ID_VALUE);

    assertEquals(
        true,
        crmsAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null));
  }
}
