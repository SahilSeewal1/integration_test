package com.kotak.ra.uams.integration.constants;

import org.testcontainers.utility.DockerImageName;

/** The type Test containers constants. */
public class TestcontainersConstants {
  /** The constant LOCALSTACK_IMAGE_NAME. */
  public static final DockerImageName LOCALSTACK_IMAGE_NAME =
      DockerImageName.parse("localstack/localstack:3");

  /** The constant INIT_FILE_NAME. */
  public static final String INIT_FILE_NAME = "init-dynamodb.sh";

  /** The constant INIT_FILE_PATH_IN_CONATINER. */
  public static final String INIT_FILE_PATH_IN_CONTAINER =
      "/etc/localstack/init/ready.d/init-dynamodb.sh";
}
