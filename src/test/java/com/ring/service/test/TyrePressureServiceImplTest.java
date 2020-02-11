package com.ring.service.test;

import static org.testng.Assert.assertEquals;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ring.dao.TyrePressureDao;
import com.ring.exceptions.RingException;
import com.ring.service.TyrePressureServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TyrePressureServiceImplTest {

  @Mock
  TyrePressureDao dao;

  @InjectMocks
  TyrePressureServiceImpl service;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getLatestPressureDataTest1() throws RingException {
    DBObject data1 = new BasicDBObject();
    data1.put("_id", "FR");
    data1.put("lastUpdatedTime", "1542100183");

    List<DBObject> dataList = new ArrayList<>();
    dataList.add(data1);

    Mockito.when(dao.getLatestPressureData(1, 2)).thenReturn(dataList);
    assertEquals(-1, service.getLatestPressureData(1, 2).get("FL"));

  }

  @Test
  public void getTyrePressureDataTest1() throws RingException {
    DBObject data1 = new BasicDBObject();
    data1.put("date", 17838);
    data1.put("inflationStartPressure", 16);
    data1.put("inflationEndPressure", 32);

    List<DBObject> dataList = new ArrayList<>();
    dataList.add(data1);

    Mockito
        .when(dao.getTyrePressureData(Mockito.anyInt(), Mockito.anyInt(),
            Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(dataList);
    
    service.getTyrePressureData(1, 2, "JAN2018", "DEC2018", "FL");
  }

}
