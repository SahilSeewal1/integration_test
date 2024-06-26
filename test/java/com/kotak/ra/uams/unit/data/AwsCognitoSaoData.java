package com.kotak.ra.uams.unit.data;

import com.kotak.ra.uams.model.LoginRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** The type Aws cognito sao data. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AwsCognitoSaoData {
  /** The constant TOKENS_RESPONSE_BODY. */
  public static final String TOKENS_RESPONSE_BODY =
      "{\"id_token\":\"eyJraWQiOiIrYmptdzgrbk1MYTRoMHI1S083cEIwZTdmXC9BVkVlRHZwTjVzeU44Y2xObz0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiUTVZc2VrOFpwTWlkZzl1ejhxVnk3USIsInN1YiI6ImVmZjk4NzNiLWZhYzMtNGI2YS05YTY0LTJkYjViODU3ZjJlNCIsImNvZ25pdG86Z3JvdXBzIjpbIlhZWiIsIlVTRVIiXSwiZW1haWxfdmVyaWZpZWQiOnRydWUsImN1c3RvbTpjdXN0b21WYXIiOiJYWVoiLCJjb2duaXRvOnByZWZlcnJlZF9yb2xlIjoiYXJuOmF3czppYW06OjAwNDQwNDY0MTg0NTpyb2xlXC9hbXBsaWZ5LWxvZ2luLWxhbWJkYS1iYWViYTgxOSIsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2U0ekw0MHA2TiIsImNvZ25pdG86dXNlcm5hbWUiOiJlZmY5ODczYi1mYWMzLTRiNmEtOWE2NC0yZGI1Yjg1N2YyZTQiLCJvcmlnaW5fanRpIjoiMGNlZTNlYzctZDZkOS00ZDllLTg2MzYtNDAzMDA2OWQ0ZjNhIiwiY29nbml0bzpyb2xlcyI6WyJhcm46YXdzOmlhbTo6MDA0NDA0NjQxODQ1OnJvbGVcL2FtcGxpZnktbG9naW4tbGFtYmRhLWJhZWJhODE5Il0sImF1ZCI6IjE5a3VuZ3ZxczFkbWkycTMzNW5mamd0YTdsIiwiZXZlbnRfaWQiOiI3ZmQ4N2IxNy1lMDdmLTRlM2QtOGZjZC01MzFlZGJlZTFlMmMiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY5NzgyMDQ5OSwiY3VzdG9tOnVzZXItaWQiOiJLTUJMMTIzIiwiZXhwIjoxNjk3ODI0MDk5LCJpYXQiOjE2OTc4MjA0OTksImp0aSI6IjFhYjllY2RlLWJhZjYtNDJmMC1iMzZkLTRjNWQ3MDZjM2ExZCIsImVtYWlsIjoic2FoaWwuc2Vld2FsQGtvdGFrLmNvbSJ9.K9IVyiKou67y7ON0hveu4d3Kh0Ywp1X-lWVkvaAP4qEw6tevwhsjzBoiLmeadfY9yXqzSwIDQqEAhhqWnVejMdsgxhu3hR3aBx3MibdFXnIr7lbZp-Faj4KNUNyy3qnTFvLoNRVA-d04nB9iQsVrIUBiS6uCPyLiCJ_iY30PmnSi1Ku8Up6EFnNLNDcPyNYo6A4YAsEBslJcY_GbBDc-Pz5jgRAU7SIxgUi2ScdYhYz5BpjKgOsQEoNavZkVvgEXnOys9BSb1hFnTdqLmM9gyzEu2Ic9FSXyRBA9qaBCuw86aEOqHxzeJKxasDeNC1juaO44WvnUzM55ioe6-fqZbQ\",\"access_token\":\"eyJraWQiOiIrdE5vaUdsTnVJSHdtRXhBUEFMRTVHdlJuQ29JeHk2Tm5cL0s0MGtKa2VjQT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJlZmY5ODczYi1mYWMzLTRiNmEtOWE2NC0yZGI1Yjg1N2YyZTQiLCJjb2duaXRvOmdyb3VwcyI6WyJYWVoiLCJVU0VSIl0sImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2U0ekw0MHA2TiIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjE5a3VuZ3ZxczFkbWkycTMzNW5mamd0YTdsIiwib3JpZ2luX2p0aSI6IjBjZWUzZWM3LWQ2ZDktNGQ5ZS04NjM2LTQwMzAwNjlkNGYzYSIsImV2ZW50X2lkIjoiN2ZkODdiMTctZTA3Zi00ZTNkLThmY2QtNTMxZWRiZWUxZTJjIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSIsImF1dGhfdGltZSI6MTY5NzgyMDQ5OSwiZXhwIjoxNjk3ODI0MDk5LCJpYXQiOjE2OTc4MjA0OTksImp0aSI6IjI0M2JmNWJiLWVkNjYtNDA5Yy04YWQwLTZjMDU4NDRiZWE3OCIsInVzZXJuYW1lIjoiZWZmOTg3M2ItZmFjMy00YjZhLTlhNjQtMmRiNWI4NTdmMmU0In0.Dg2vjPaDCyJyTRBJeVgF1Yj0Nq5317Z5D4GgclHLF59t93-g2da6WZXd0epv2OdnEblQqjkkvfzv0UfDfWr-nS075hTFQbM4uWW-p-50aOreKPXcDFDnwCzMFLWvzyk_Om-y6XlC95T5ff3d9LWJcfNenrWYKmk8i6jlTfNYC7oX-apUKmcxVvXnCWLU7jwaobAASKxBhPzao7tk5xRglxYNC_0sD379ZYuxmhNclaBPsaDqCxJdvfLj7I8UurvyG_vclf9nWlLj2vXpCfWa-msmBSox7v6pW-5Tr5r2SPNDxsOtLbol6dA6-q5JTt4pVefjkIygqd3SBrxD3vywtA\",\"refresh_token\":\"eyJjdHkiOiJKV1QiLCJlbmMiOiJBMjU2R0NNIiwiYWxnIjoiUlNBLU9BRVAifQ.IUdVhz8Z5Oh6BxUnSJFyaVFqT_QYzuHL8g8tr0y0IwCarG4qzAVQhR3Anp2FRAiX4F9XjDM85umWCWNeYtDfV238w_Nsjm9TpZtXNeHm3d27vaaQwnl48fmA514rE5QF_ublJweUy1L1WNlELGsCp7Jav36nRjAl00OX5m7urYEhNL_nBGRkRJlQhi15mK4-d1J_tOtZSWUtrm108dfuthewidwLx_ZewixOAYFUWeCfQvleGrZGNoe7hF9aXUAVuhnrPhiNT0UwpNM4uPgMeq-li8DTgzR6oPCfXYfzDseZI6SmWsSQ-BNysKEsWUuSXBjnDvs22ozdNNyiYEq-fg.P1lQxen63kTpEoxQ.xkvJeQD0Nmjdx3Llm60ZXCCNcew_IYUVjlz-vyd-qA22XvNNgnRz2paqwcsFWywT-UwyDnDhV7qGQxi-SwR25Osos8eJiJzzoFM6HYVWPkM0bU7RvVxu0WoUKzKHQeAQOxTemCK5v-OuyQCFcdl03fXc-Ny8U3ykAyw7ssRW8rlruGC6ssZIQsQ-8K0t2097SScUAztJom94O63FAIN2l8N3SEUkOsQVhUETIOTOg15bQwCZBYkxhhWwZyzC6qbvi64EEuFrAHCC3V2hMnPK8m2s2NQFYZaUt59XNTvkKtb0Rza0ZGE6LT4UNxYW8yLMSBZ2A0TLmio1EIy8y7Daau0zFlV9DYtCuv3NytOJ27_Bv-rlVQ4VjRDvaSEf_YJVoRRea2eOdW0bgJFbUQJFlCDj2NLgMJlHA7Qv3J7x_su9ODpCmH9a4zZhFzKluGJJjBkRDRQw5jYaRx2Raa0Le_wdNr8bZMsmNLT1YVxlelv1Gk4HJyBzbBxenKlmOPzA3xrfiuzQ67wE0N1crAWiVULzyCB6BBxBivETEz3_DLjSOoGV_j97BOy20mc4jnVm11yWvQ5Ryn1hC0SQ5R10il0N6au3Fz3tPdqezSHzQMdgCkvILrrs2wHjlGznn6dVjTmSyAPuJMjkwfpy74xvShPfM7amvfog9Bb5xqVepT3SulvqdQlLmGxkADW2QkwPIghVzoWq6Mz21P2G6SAU1zmyo7RmDDnYG7PlqjqcI6HCGk-sxx4tqUqWj1JzkSDhphruGZ31H90EdqhyO4xaTwKkB3oOkjH6s8xEMotCVLnh7mKgNwiE9hvysV2Jpy1i-eUCpkqtlQAsE42sOZuLCMfz7cjJucw2B6uOzWSHMvFR_qpVAtXT7iofrwRJuPQIGrSELPnCjCJHg_JaS3vsgQLelEqdgghWn-GIR2ioLEjFLyJ5I07jxHBCqwTU7uRRTJJvs-SWqHj3M3zDcD78nVBsRcdt0XCqAigWMDIKp7ZjWjXd7wt3qPqeZuyAssnQ8kQUYugbpHVpKHAwRiEp72RaCOCqk_qFhQJdBI9rsv92ru4ONTGSyyaNpFdbNsUMXi9nRYeqMbMwJWhDyD-YsN5lH8F7Zj8YjvE8oB0WoOYo3WcTnMfd4o_cYHanYCBWjsl8AkmaOqFDl51lkSrks_WW4em5iPz9yAx0maeVL9BvzPqRsxHBx_iCycXhU_F83QAmqM4VQcACJQLk6whsSa-b9o5hBveBmRHwL4J92A97bG7B2VKwVSJvSvs47D-a3LKKl2p5lK6Fx8_YyMGqxTlonwyMIDoNnRDLYPaY5lSbErNhJBco3g.BZwSIt3Y5L3yl5wUyQdWyA\",\"expires_in\":3600,\"token_type\":\"Bearer\"}";

  /** The constant TOKEN_REQUEST_OBJ. */
  public static final LoginRequest TOKEN_REQUEST_OBJ =
      new LoginRequest(
          "9JVpQMZ5DpMwocQPq4bbtbiznXGhekdjhOirKL3VRWt5ijvJbJogliHPscNBtaxXK9zpUC3Pu04yuxasD25nTSxU9KWydgyQlBYeGJcBKfNyM4u44LZ8PVfojS3SPxEL",
          "f9086f52-4341-4459-b721-e0774f006a49",
          "19kungvqs1dmi2q335nfjgta9m",
          "https://abc.com/",
          "sample-collection.auth.us-east-1.amazoncognito.com");

  /** The constant REFRESH_TOKEN. */
  public static final String REFRESH_TOKEN = "ASDFGHJKLQWERTYUIOPP";

  /** The constant USER_INFO_RESPONSE_BODY. */
  public static final String USER_INFO_RESPONSE_BODY =
      "{\"access_token\": \"eyjrqwertyuiolasdfghjklsdfgjkl.asdfghjklwertyuiozxcvbnm.asdfghjklk1234asdfghjk\", \"id_token\": \"eyjrasdfghjklqwertyuiopzxcvbnm.asdfghjklqwertyuiozxcvbnm.qwertyuioasdfghjklertyui\"}";

  /** The constant RESPONSE. */
  public static final ResponseEntity RESPONSE =
      ResponseEntity.status(HttpStatus.OK).body(USER_INFO_RESPONSE_BODY);

  /** The constant TOKEN. */
  public static final String TOKEN = "ASDFGHJKLQWERTYUIOPP";

  /** The constant RESPONSE_BODY. */
  public static final String RESPONSE_BODY =
      "{\"access_token\": \"eyjrqwertyuiolasdfghjklsdfgjkl.asdfghjklwertyuiozxcvbnm.asdfghjklk1234asdfghjk\", \"id_token\": \"eyjrasdfghjklqwertyuiopzxcvbnm.asdfghjklqwertyuiozxcvbnm.qwertyuioasdfghjklertyui\"}";

  /** The constant RESPONSE_ENTITY. */
  public static final ResponseEntity RESPONSE_ENTITY =
      ResponseEntity.status(HttpStatus.OK).body(RESPONSE_BODY);
}
