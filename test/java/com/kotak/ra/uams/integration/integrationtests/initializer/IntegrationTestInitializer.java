package com.kotak.ra.uams.integration.integrationtests.initializer;

import static com.kotak.ra.uams.integration.data.UserDetailsData.USER_DETAILS_ACTIVE;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.entity.UserDetails;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.EncryptRequest;

public class IntegrationTestInitializer implements BeforeAllCallback {
  public static DynamoDBMapper userDetailsMapper;
  public static KmsClient awsKmsCLient;

  @Override
  public void beforeAll(final ExtensionContext context) {
    for (UserDetails userDetails : USER_DETAILS_ACTIVE) {
      userDetails.setAccessToken(encryptData(userDetails.getAccessToken()));
      userDetails.setRefreshToken(encryptData(userDetails.getRefreshToken()));
      userDetails.setIdToken(encryptData(userDetails.getIdToken()));
      userDetailsMapper.save(userDetails);
    }
  }

  private String encryptData(final String data) {
    final EncryptRequest encryptRequest =
        EncryptRequest.builder()
            .keyId(awsKmsCLient.listKeys().keys().get(0).keyId())
            .plaintext(SdkBytes.fromUtf8String(data))
            .build();

    return new String(
        Base64.getUrlEncoder()
            .encode(awsKmsCLient.encrypt(encryptRequest).ciphertextBlob().asByteArray()),
        StandardCharsets.UTF_8);
  }
}
