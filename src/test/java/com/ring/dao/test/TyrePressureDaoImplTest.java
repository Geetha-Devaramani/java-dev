package com.ring.dao.test;

import static org.testng.Assert.assertEquals;

import com.mongodb.DBObject;
import com.ring.dao.TyrePressureDaoImpl;
import com.ring.exceptions.RingException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TyrePressureDaoImplTest {

  @Mock
  MongoTemplate mongoTemplate;

  @Mock
  AggregationResults<DBObject> aggrResults;

  @InjectMocks
  TyrePressureDaoImpl dao;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getLatestPressureDataTest1() throws RingException {
    List<DBObject> dataList = new ArrayList<>();
    Mockito
        .when(mongoTemplate.aggregate(Mockito.any(Aggregation.class),
            Mockito.anyString(), Mockito.any(Class.class)))
        .thenReturn(aggrResults);
    Mockito.when(aggrResults.getMappedResults()).thenReturn(dataList);
    assertEquals(dataList, dao.getLatestPressureData(1, 2));
  }
  
  @Test
  public void getTyrePressureDataTest1() throws RingException {
    List<DBObject> dataList = new ArrayList<>();
    Mockito
        .when(mongoTemplate.aggregate(Mockito.any(Aggregation.class),
            Mockito.anyString(), Mockito.any(Class.class)))
        .thenReturn(aggrResults);
    Mockito.when(aggrResults.getMappedResults()).thenReturn(dataList);
    assertEquals(dataList, dao.getTyrePressureData(1, 1, 17838, 17855, "FL"));
  }

}
