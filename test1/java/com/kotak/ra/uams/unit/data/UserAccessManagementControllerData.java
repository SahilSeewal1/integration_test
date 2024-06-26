package com.kotak.ra.uams.unit.data;

import static com.kotak.ra.uams.constant.CookieGeneratorConstants.ACCESS_TOKEN_DELIMITER;
import static com.kotak.ra.uams.constant.ResponseMessage.LOGOUT_FLOW_SUCCESS;
import static com.kotak.ra.uams.constant.ResponseMessage.REFRESH_FLOW_NEW_ACCESS_TOKEN;

import com.kotak.ra.uams.constant.CookieGeneratorConstants;
import com.kotak.ra.uams.model.AccessTokenAndUserId;
import com.kotak.ra.uams.model.LoginRequest;
import com.kotak.ra.uams.model.LogoutRequest;
import com.kotak.ra.uams.model.LogoutResponse;
import com.kotak.ra.uams.model.RefreshRequest;
import com.kotak.ra.uams.model.RefreshTokenResponse;
import jakarta.servlet.http.Cookie;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The type User access management controller data.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserAccessManagementControllerData {

  /** The constant TOKEN_RESPONSE. */
  public static final AccessTokenAndUserId TOKEN_RESPONSE =
      new AccessTokenAndUserId(
          "eyJraWQiOiJBUU01WnJ4NEFaV1dGRlM4dmtmWGNHbUNQdm45VEU5T1VNZW03c3lRc2drPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyNGQ4MDQ5OC1hMDcxLTcwMTUtNmRlOC03OTg3Y2E2ZGUyOTgiLCJjb2duaXRvOmdyb3VwcyI6WyJVU0VSIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX21qUVZoY29QQiIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjNwOWRiZGFvN2NzOG5vOXEzYm9pamU5anFiIiwib3JpZ2luX2p0aSI6IjRmMDk0MThjLTY0MTUtNDBmMC04NTMzLWQ3MGFkMjEyYzYyMyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUiLCJhdXRoX3RpbWUiOjE2OTAxODU0MTAsImV4cCI6MTY5MDE4NTcxMCwiaWF0IjoxNjkwMTg1NDEwLCJqdGkiOiI5NTQyMDM1MS04M2ZiLTRiMjQtYjQxOS1kNWQ1YWNlYTc1ODQiLCJ1c2VybmFtZSI6IjI0ZDgwNDk4LWEwNzEtNzAxNS02ZGU4LTc5ODdjYTZkZTI5OCJ9.vkMYALNhtnzM_kPo197gINvjDOaFH_SScauFoszQWnTLcHqtQ7XHeoJqu7GU2gU2Y4-U2y6PkWzUJ2ZUZWWGlXdaE-ybrifpYcS0CZ6YobQl7ImvzyiXOovjy6szP8MG79iR9EB49Hinq9YXFDHvvxPTxvK5js65O6hBcGSTrNHhrjjSugSiqOj7_WMbSPQALzWBva0IxRbXh5zyUJSkZ8bWjsVjPg1d-9t6_Dv_x8DaITtxY5yYWHbVl_SlrXBPWzjgKIeIyBCcM1_wmYBcOyYRHeyarcR2sEd5YPFvz9jPEIkoeKIy57oiy6IPvzhqWtSE2PGYoISjXU-41mzO5Q",
          "KMBL123");

  /** The constant TOKEN. */
  public static final String TOKEN =
      TOKEN_RESPONSE.getAccessToken() + ACCESS_TOKEN_DELIMITER + TOKEN_RESPONSE.getUserId();

  /** The constant REQUEST_ID. */
  public static final UUID REQUEST_ID = UUID.fromString("18243266-c3d1-457d-9195-ef391248fd11");

  /** The constant TOKEN_REQUEST_OBJECT. */
  public static final LoginRequest TOKEN_REQUEST_OBJECT =
      new LoginRequest(
          "9JVpQMZ5DpMwocQPq4bbtbiznXGhekdjhOirKL3VRWt5ijvJbJogliHPscNBtaxXK9zpUC3Pu04yuxasD25nTSxU9KWydgyQlBYeGJcBKfNyM4u44LZ8PVfojS3SPxEL",
          "f9086f52-4341-4459-b721-e0774f006a49",
          "19kungvqs1dmi2q335nfjgta9m",
          "https://abc.com/",
          "sample-collection.auth.us-east-1.amazoncognito.com");

  /** The constant COOKIE. */
  public static final Cookie COOKIE =
          new Cookie(
                  UserAccessManagementControllerData.TOKEN_REQUEST_OBJECT.getClientId()
                          + CookieGeneratorConstants.COOKIE_SUFFIX,
                  UserAccessManagementControllerData.TOKEN);

  /** The constant CLIENT_AND_DOMAIN_INFO. */
  public static final RefreshRequest REFRESH_REQUEST =
          new RefreshRequest(
                  "19kungvqs1dmi2q335nfjgta9m", "sample-collection.auth.us-east-1.amazoncognito.com");

  public static final LogoutRequest LOGOUT_REQUEST =
          new LogoutRequest(
                  "19kungvqs1dmi2q335nfjgta9m", "sample-collection.auth.us-east-1.amazoncognito.com");

  /** The constant SUCCESSFUL_LOGOUT_RESPONSE. */
  public static final ResponseEntity SUCCESSFUL_LOGOUT_RESPONSE =
          ResponseEntity.status(HttpStatus.OK).body(new LogoutResponse(LOGOUT_FLOW_SUCCESS));

  /** The constant SUCCESSFUL_REFRESH_RESPONSE. */
  public static final ResponseEntity SUCCESSFUL_REFRESH_RESPONSE =
          ResponseEntity.status(HttpStatus.ACCEPTED)
                  .body(new RefreshTokenResponse(REFRESH_FLOW_NEW_ACCESS_TOKEN));
}
