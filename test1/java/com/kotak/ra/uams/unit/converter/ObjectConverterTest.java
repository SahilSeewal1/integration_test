package com.kotak.ra.uams.unit.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotak.ra.uams.converter.ObjectConverter;
import com.kotak.ra.uams.model.UserInfo;
import com.kotak.ra.uams.unit.data.ObjectConverterData;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/** The type Parser util test. */
@Log4j2
public class ObjectConverterTest {
  /** The Parser util. */
  @InjectMocks
  private ObjectConverter objectConverter;

  /** The Object mapper. */
  @Mock private ObjectMapper objectMapper;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Json string parser test. */
  @Test
  @DisplayName("Test json string parser")
  public void jsonStringParserTest() {

    try {
      Mockito.when(objectMapper.readValue(ObjectConverterData.ID_TOKEN_PAYLOAD, UserInfo.class))
          .thenReturn(ObjectConverterData.USER_INFO);
      UserInfo actualUserId =
          objectConverter.parse(ObjectConverterData.ID_TOKEN_PAYLOAD, UserInfo.class);

      assertEquals(ObjectConverterData.USER_INFO.getCognitoUserId(), actualUserId.getCognitoUserId());
      assertEquals(ObjectConverterData.USER_INFO.getAdfsUserId(), actualUserId.getAdfsUserId());
    } catch (Exception e) {
      log.error("Error occurred while executing test on json string parser: {}", e);
    }
  }

  @Test
  @DisplayName("Test json string parser exception")
  public void jsonStringParserExceptionTest() {
    try {

      Mockito.when(objectMapper.readValue(ObjectConverterData.ID_TOKEN_PAYLOAD, UserInfo.class))
          .thenThrow(new ParseException());
      assertThrows(
          ParseException.class,
          () -> objectConverter.parse(ObjectConverterData.ID_TOKEN_PAYLOAD, UserInfo.class));

    } catch (Exception e) {
      log.error("Error occurred while executing test on json string parser: {}", e);
    }
  }
}
