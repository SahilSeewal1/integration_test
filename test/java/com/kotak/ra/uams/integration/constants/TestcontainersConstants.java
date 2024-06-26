package com.kotak.ra.uams.integration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testcontainers.utility.DockerImageName;

/** The type Test containers constants. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestcontainersConstants {
  /** The constant LOCALSTACK_IMAGE_NAME. */
  public static final DockerImageName LOCALSTACK_IMAGE_NAME =
      DockerImageName.parse("localstack/localstack:3");

  /** The constant INIT_FILE_NAME. */
  public static final String INIT_DYNAMO_FILE_NAME = "init-dynamodb.sh";

  /** The constant INIT_KMS_FILE_NAME. */
  public static final String INIT_KMS_FILE_NAME = "init-kms.sh";

  /** The constant INIT_FILE_PATH_IN_CONATINER. */
  public static final String INIT_DYNAMO_FILE_PATH_IN_CONTAINER =
      "/etc/localstack/init/ready.d/init-dynamodb.sh";

  /** The constant INIT_KMS_FILE_PATH_IN_CONTAINER. */
  public static final String INIT_KMS_FILE_PATH_IN_CONTAINER =
      "/etc/localstack/init/ready.d/init-kms.sh";
}
