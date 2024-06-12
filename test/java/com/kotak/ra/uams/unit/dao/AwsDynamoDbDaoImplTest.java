package com.kotak.ra.uams.unit.dao;

import static com.kotak.ra.uams.constant.ResponseMessage.DYNAMO_DB_SAVE_ITEM_FAILURE;
import static com.kotak.ra.uams.unit.data.AwsDynamoDbDaoData.ID;
import static com.kotak.ra.uams.unit.data.AwsDynamoDbDaoData.INVALID_USER_DETAILS;
import static com.kotak.ra.uams.unit.data.AwsDynamoDbDaoData.VALID_USER_DETAILS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kotak.ra.uams.dao.impl.AwsDynamoDbDaoImpl;
import com.kotak.ra.uams.entity.UserDetails;
import com.kotak.ra.uams.exception.UamsDbException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type Aws dynamo db dao test. */
public class AwsDynamoDbDaoImplTest {
    /** The Mapper. */
    @Mock(name = "userDetailsTable")
    private DynamoDBMapper mapper;

  /** The Aws dynamo db dao. */
  @InjectMocks private AwsDynamoDbDaoImpl awsDynamoDbDao;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /** Successfully save user details. */
    @Test
    @DisplayName("Successful user details save test.")
    public void successfullySaveUserDetails() {

    doNothing().when(mapper).save(VALID_USER_DETAILS);
    awsDynamoDbDao.setItem(VALID_USER_DETAILS, mapper);
    verify(mapper).save(VALID_USER_DETAILS);
    }

    /**
     * Unsuccessfully save user details.
     */
    @Test
    @DisplayName("Unsuccessful user details save test.")
    public void unsuccessfullySaveUserDetails() {

        doThrow(new UamsDbException(DYNAMO_DB_SAVE_ITEM_FAILURE))
                .when(mapper)
                .save(INVALID_USER_DETAILS);
    assertThrows(
        UamsDbException.class,
        () -> {
          awsDynamoDbDao.setItem(INVALID_USER_DETAILS, mapper);
        });
    }

    /** Fetch user details. */
    @Test
    @DisplayName("Fetch user details test.")
    public void fetchUserDetails() {

    when(mapper.load(UserDetails.class, ID)).thenReturn(VALID_USER_DETAILS);
    assertEquals(VALID_USER_DETAILS, awsDynamoDbDao.getItem(ID, UserDetails.class, mapper));
    }

    /**
     * Un successful fetch user details.
     */
    @Test
    @DisplayName("Unsuccessful attempt to fetch user details test.")
    public void unSuccessfulFetchUserDetails() {

        doThrow(new UamsDbException()).when(mapper).load(UserDetails.class, ID);

    assertThrows(
        UamsDbException.class,
        () -> {
          awsDynamoDbDao.getItem(ID, UserDetails.class, mapper);
        });
    }
}
