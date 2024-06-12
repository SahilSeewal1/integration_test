package com.kotak.ra.uams.unit.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kotak.ra.uams.helper.CookieHelper;
import com.kotak.ra.uams.unit.data.AwsKmsSaoData;
import com.kotak.ra.uams.unit.data.CookieUtilData;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * The type Cookie util test.
 */
public class CookieHelperTest {

  /** The Cookie util. */
  @InjectMocks private CookieHelper cookieHelper;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Create http only cookie. */
  @Test
  @DisplayName("Test HttpOnly cookie creation.")
  public void createHttpOnlyCookie() {

    Cookie actualCookie =
        cookieHelper
            .builder("abc123_Token", AwsKmsSaoData.ACCESS_TOKEN + ".xyz")
            .setHttpOnly(true)
            .setPath("/")
            .setSecure(true)
            .setMaxAge(1000)
            .setSameSite("strict")
            .build();

    assertEquals(CookieUtilData.COOKIE.getName(), actualCookie.getName());
    assertEquals(CookieUtilData.COOKIE.getValue(), actualCookie.getValue());
    assertEquals("/", actualCookie.getPath());
    assertEquals(true, actualCookie.isHttpOnly());
    assertEquals(true, actualCookie.getSecure());
    assertEquals(1000, actualCookie.getMaxAge());
    assertEquals("strict", actualCookie.getAttribute("SameSite"));
  }
}
