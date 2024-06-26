package com.kotak.ra.uams.unit.data;

import com.kotak.ra.uams.model.UserInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** The type Object converter data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectConverterData {
  /** The constant ID_TOKEN. */
  public static final String ID_TOKEN =
      "eyJraWQiOiJGeWk4S3JSWFZXWUZHZlV2TmM4RmwxOGk4MXluTnRuMTRXXC9Cc01NT2tGND0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiaTcxb0w0SzcxQWR4Wi1kTENCMEFVdyIsInN1YiI6IjI0ZDgwNDk4LWEwNzEtNzAxNS02ZGU4LTc5ODdjYTZkZTI5OCIsImNvZ25pdG86Z3JvdXBzIjpbIlVTRVIiXSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfbWpRVmhjb1BCIiwiY29nbml0bzp1c2VybmFtZSI6IjI0ZDgwNDk4LWEwNzEtNzAxNS02ZGU4LTc5ODdjYTZkZTI5OCIsIm9yaWdpbl9qdGkiOiI0ZjA5NDE4Yy02NDE1LTQwZjAtODUzMy1kNzBhZDIxMmM2MjMiLCJhdWQiOiIzcDlkYmRhbzdjczhubzlxM2JvaWplOWpxYiIsImN1c3RvbTpJQW1Gcm9tQ29nbml0bzEyMyI6Ill1cCIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNjkwMTg1NDEwLCJleHAiOjE2OTAxODU3MTAsImlhdCI6MTY5MDE4NTQxMCwianRpIjoiZmFmYzgxMzYtMDIxZS00Yjc0LWFlNTYtZWZhOGMwMGE3YTQ5IiwiZW1haWwiOiJzYWhpbC5zZWV3YWxAa290YWsuY29tIn0.N4gv5xrcSYoumSnsRTrr45WppcZzvpIL9hBHfQDuRa-uzsZay8F5C9prCELo-PsI_pVPiriTECDxhxBSFr8gN87kDl55kiIGOKGrKG13J51CR6lY4uVwP7RchMosG8_iW8zv8VAoRPfKqr0lziJ6gjZd76LSQmKpFvj_hxZ0vL-Q-Sxwu3q3bPnIKOYjSvBA5jfc5kDpXdbMbNWPjoRH3j0PgZr0fauDwc2ZScPwM3caU8iFM8oYpOm3b56pn5why-Q-V7qcf2EVhhLI4WjM6eTl_6l_rVd-zXvdBvtAwYxXa4sOIqui_3Le8KMtGutaj6GkrnjT_zfN7OectMUtiw";

  /** The constant ID_TOKEN_PAYLOAD. */
  public static final String ID_TOKEN_PAYLOAD =
      "{\"at_hash\":\"i71oL4K71AdxZ-dLCB0AUw\",\"sub\":\"24d80498-a071-7015-6de8-7987ca6de298\",\"cognito:groups\":[\"USER\"],\"iss\":\"https:\\/\\/cognito-idp.us-east-1.amazonaws.com\\/us-east-1_mjQVhcoPB\",\"cognito:username\":\"24d80498-a071-7015-6de8-7987ca6de298\",\"origin_jti\":\"4f09418c-6415-40f0-8533-d70ad212c623\",\"aud\":\"3p9dbdao7cs8no9q3boije9jqb\",\"custom:IAmFromCognito123\":\"Yup\",\"token_use\":\"id\",\"auth_time\":1690185410,\"exp\":1690185710,\"iat\":1690185410,\"jti\":\"fafc8136-021e-4b74-ae56-efa8c00a7a49\",\"email\":\"sahil.seewal@kotak.com\"}";

  /** The constant USER_ID. */
  public static final UserInfo USER_INFO =
      new UserInfo(null, "24d80498-a071-7015-6de8-7987ca6de298", "abc", "abc@kotak.com");
}
