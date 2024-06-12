package com.kotak.ra.uams.unit.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Crms authentication interceptor data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrmsAuthenticationInterceptorData {
  /** The constant AUTHORIZATION. */
  public static final String AUTHORIZATION = "Authorization";

  /** The constant INVALID_JWT. */
  public static final String INVALID_JWT =
      "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDYWxsX1JlY29yZGluZ19TZXJ2aWNlIiwiWC1SZXF1ZXN0LUlEIjoiYjc2MWQ5MTItZjYyMC00YjFmLWJiNjAtNGM2MjBjOTI2MjQ1IiwiaWF0IjoxNzE2MjAzODk2LCJqdGkiOiIwMDJjY2U5Ny1mMmU2LTQwMmMtODIyYi0xMTkwMTNhMDZkZjUifQ.VyAR6QfZRQzHAdnAp00B70Wv_pE3-TOtUG4y2Tq9rD4";

  /** The constant EXPIRED_JWT. */
  public static final String EXPIRED_JWT =
          "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDYWxsX1JlY29yZGluZ19TZXJ2aWNlIiwiWC1SZXF1ZXN0LUlEIjoiYjc2MWQ5MTItZjYyMC00YjFmLWJiNjAtNGM2MjBjOTI2MjQ1IiwiaWF0IjoxNzE2MjAzODk2LCJqdGkiOiIwMDJjY2U5Ny1mMmU2LTQwMmMtODIyYi0xMTkwMTNhMDZkZjUiLCJleHAiOjE2MTY4OTQ3Mjh9.cFZtqEwWEzpvlxjcD8SJjunuOKITyoVLquI9vdUsrMw";

  /** The constant REQUEST_ID_KEY. */
  public static final String REQUEST_ID_KEY = "X-Request-ID";

  /** The constant REQUEST_ID_VALUE. */
  public static final String REQUEST_ID_VALUE = "b761d912-f620-4b1f-bb60-4c620c926245";

  /** The constant SECRET_KEY. */
  public static final String SECRET_KEY = "xyz";

  /** The constant VALID_JWT. */
  public static final String VALID_JWT =
      "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDYWxsX1JlY29yZGluZ19TZXJ2aWNlIiwiWC1SZXF1ZXN0LUlEIjoiYjc2MWQ5MTItZjYyMC00YjFmLWJiNjAtNGM2MjBjOTI2MjQ1IiwiaWF0IjoxNzE2MjAzODk2LCJqdGkiOiIwMDJjY2U5Ny1mMmU2LTQwMmMtODIyYi0xMTkwMTNhMDZkZjUifQ.pWkkZYwksFlkKdpKJkYbKqbVuIiIb-ZO_M2dUrlc8Po";
}
