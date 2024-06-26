package com.kotak.ra.uams.unit.data;

import jakarta.servlet.http.Cookie;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Cookie util data.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtilData {
  /** The constant COOKIE. */
  public static final Cookie COOKIE = new Cookie("abc123_Token", AwsKmsSaoData.ACCESS_TOKEN + ".xyz");
}
