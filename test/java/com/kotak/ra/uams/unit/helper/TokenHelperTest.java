package com.kotak.ra.uams.unit.helper;

import static com.kotak.ra.uams.unit.data.ObjectConverterData.ID_TOKEN_PAYLOAD;
import static com.kotak.ra.uams.unit.data.TokenHelperData.TOKEN;
import static com.kotak.ra.uams.unit.data.TokenHelperData.TOKEN_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kotak.ra.uams.helper.TokenHelper;
import com.kotak.ra.uams.sao.impl.AuthenticationTokenSaoImpl;
import com.kotak.ra.uams.unit.data.ObjectConverterData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The type Token helper test.
 */
public class TokenHelperTest {

  /** The Aws cognito sao. */
  @Mock
  private AuthenticationTokenSaoImpl awsCognitoSaoImpl;

  /** The Token helper. */
  @InjectMocks private TokenHelper tokenHelper;

  /**
   * Sets .
   */
@BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Id token payload parser test.
   */
@Test
  @DisplayName("Test id token payload parser")
  public void idTokenPayloadParserTest() {

    assertEquals(ID_TOKEN_PAYLOAD, tokenHelper.getIdTokenPayload(ObjectConverterData.ID_TOKEN));
  }

  /**
   * Gets access token and user id test.
   */
@Test
  @DisplayName("Test to fetch id token and user id from provided string")
  public void getAccessTokenAndUserIdTest() {

    assertEquals(TOKEN_RESPONSE, tokenHelper.getAccessTokenAndUserId(TOKEN));
  }
}
