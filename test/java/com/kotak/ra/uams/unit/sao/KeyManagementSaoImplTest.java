package com.kotak.ra.uams.unit.sao;

import static com.kotak.ra.uams.constant.ResponseMessage.KMS_DECRYPTION_FAILURE;
import static com.kotak.ra.uams.constant.ResponseMessage.KMS_ENCRYPTION_FAILURE;
import static com.kotak.ra.uams.unit.data.AwsKmsSaoData.ACCESS_TOKEN;
import static com.kotak.ra.uams.unit.data.AwsKmsSaoData.BASE64_ENCODED;
import static com.kotak.ra.uams.unit.data.AwsKmsSaoData.SDK_BYTE_ARRAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.kotak.ra.uams.exception.UamsCryptographyException;
import com.kotak.ra.uams.sao.impl.KeyManagementSaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;

/** The type Aws kms util test. */
public class KeyManagementSaoImplTest {

  /** The Kms client. */
  @Mock private KmsClient kmsClient;

  /** The Encrypt response. */
  @Mock private EncryptResponse encryptResponse;

  /** The Decrypt response. */
  @Mock private DecryptResponse decryptResponse;

  /** The Sdk bytes. */
  @Mock private SdkBytes sdkBytes;

  /** The Awskms util. */
  @InjectMocks private KeyManagementSaoImpl awskmsSaoImpl;

  /** Sets . */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  /** Encrypt and encode data. */
  @Test
  @DisplayName("Encrypt and Encode data using KMS and Base64 algorithm respectively")
  public void encryptAndEncodeData() {
    when(kmsClient.encrypt(any(EncryptRequest.class))).thenReturn(encryptResponse);
    when(encryptResponse.ciphertextBlob()).thenReturn(sdkBytes);
    when(sdkBytes.asByteArray()).thenReturn(SDK_BYTE_ARRAY);

    assertEquals(BASE64_ENCODED, awskmsSaoImpl.encryptData(ACCESS_TOKEN));
  }

  /** Decrypt and decode data. */
  @Test
  @DisplayName("Decrypt and Decode data using KMS and Base64 algorithm respectively")
  public void decryptAndDecodeData() {
    when(kmsClient.decrypt(any(DecryptRequest.class))).thenReturn(decryptResponse);
    when(decryptResponse.plaintext()).thenReturn(sdkBytes);
    when(sdkBytes.asUtf8String()).thenReturn(ACCESS_TOKEN);

    assertEquals(ACCESS_TOKEN, awskmsSaoImpl.decryptData(BASE64_ENCODED));
  }

  /** Encryption kms client exception. */
  @Test
  @DisplayName("Exception occur while Encrypting data using KMS")
  public void encryptionKmsClientException() {
    doThrow(new UamsCryptographyException(KMS_ENCRYPTION_FAILURE))
        .when(kmsClient)
        .encrypt(any(EncryptRequest.class));
    assertThrows(UamsCryptographyException.class, () -> awskmsSaoImpl.encryptData(ACCESS_TOKEN));
  }

  /** Decryption kms client exception. */
  @Test
  @DisplayName("Exception occur while decrypting data using KMS")
  public void decryptionKmsClientException() {
    doThrow(new UamsCryptographyException(KMS_DECRYPTION_FAILURE))
        .when(kmsClient)
        .decrypt(any(DecryptRequest.class));
    assertThrows(UamsCryptographyException.class, () -> awskmsSaoImpl.decryptData(BASE64_ENCODED));
  }
}
