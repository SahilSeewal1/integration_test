package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.CookieGeneratorConstants.ACCESS_TOKEN_DELIMITER;

import com.kotak.ra.uams.model.AccessTokenAndUserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Token helper data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenHelperData {

  /** The constant TOKEN_RESPONSE. */
  public static final AccessTokenAndUserId TOKEN_RESPONSE =
      new AccessTokenAndUserId(
          "eyJraWQiOiJBUU01WnJ4NEFaV1dGRlM4dmtmWGNHbUNQdm45VEU5T1VNZW03c3lRc2drPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyNGQ4MDQ5OC1hMDcxLTcwMTUtNmRlOC03OTg3Y2E2ZGUyOTgiLCJjb2duaXRvOmdyb3VwcyI6WyJVU0VSIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX21qUVZoY29QQiIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjNwOWRiZGFvN2NzOG5vOXEzYm9pamU5anFiIiwib3JpZ2luX2p0aSI6IjRmMDk0MThjLTY0MTUtNDBmMC04NTMzLWQ3MGFkMjEyYzYyMyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUiLCJhdXRoX3RpbWUiOjE2OTAxODU0MTAsImV4cCI6MTY5MDE4NTcxMCwiaWF0IjoxNjkwMTg1NDEwLCJqdGkiOiI5NTQyMDM1MS04M2ZiLTRiMjQtYjQxOS1kNWQ1YWNlYTc1ODQiLCJ1c2VybmFtZSI6IjI0ZDgwNDk4LWEwNzEtNzAxNS02ZGU4LTc5ODdjYTZkZTI5OCJ9.vkMYALNhtnzM_kPo197gINvjDOaFH_SScauFoszQWnTLcHqtQ7XHeoJqu7GU2gU2Y4-U2y6PkWzUJ2ZUZWWGlXdaE-ybrifpYcS0CZ6YobQl7ImvzyiXOovjy6szP8MG79iR9EB49Hinq9YXFDHvvxPTxvK5js65O6hBcGSTrNHhrjjSugSiqOj7_WMbSPQALzWBva0IxRbXh5zyUJSkZ8bWjsVjPg1d-9t6_Dv_x8DaITtxY5yYWHbVl_SlrXBPWzjgKIeIyBCcM1_wmYBcOyYRHeyarcR2sEd5YPFvz9jPEIkoeKIy57oiy6IPvzhqWtSE2PGYoISjXU-41mzO5Q",
          "KMBL123");

  /** The constant TOKEN. */
  public static final String TOKEN =
      TOKEN_RESPONSE.getAccessToken() + ACCESS_TOKEN_DELIMITER + TOKEN_RESPONSE.getUserId();
}
