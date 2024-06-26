package com.kotak.ra.uams.integration.integrationtests.initializer;

import static com.kotak.ra.uams.constant.DbConstants.USER_DETAILS_TABLE_BEAN;
import static com.kotak.ra.uams.integration.data.UserDetailsData.USER_DETAILS_ACTIVE;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.entity.UserDetails;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.EncryptRequest;

@Component
@Log4j2
public class IntegrationTestInitializer {

  @Autowired
  @Qualifier(USER_DETAILS_TABLE_BEAN)
  private DynamoDBMapper userDetailsMapper;

  @Autowired
  private KmsClient awsKmsCLient;

  public void setupResources() {
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
