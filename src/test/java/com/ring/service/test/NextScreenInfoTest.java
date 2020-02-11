package com.ring.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.ClientBo;
import com.ring.bo.NextScreenInformationBo;
import com.ring.dao.UserDao;
import com.ring.dao.VehicleDao;
import com.ring.exceptions.RingException;
import com.ring.service.UserServiceImpl;
import com.ring.util.ResponseUtil;

@PrepareForTest({ResponseUtil.class})
@PowerMockIgnore("javax.management.*")
public class NextScreenInfoTest extends PowerMockTestCase {

  @Mock
  UserDao dao;

  @Mock
  VehicleDao userDao;
  
  @Mock
  NextScreenInformationBo next;

  @Mock
  Properties prop;

  @InjectMocks
  UserServiceImpl service;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void getNextScreeninfoTest2() throws RingException {
    List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
    ClientBo clientBo = new  ClientBo();
    clientBo.setClientSecret("RingAuto");
    clientBo.setClientId("dc45f4b1-5826-4b18-b5f8-edb413325cbd");
    clientBo.setUserId(1);
    Mockito.when(prop.getProperty(Mockito.anyString()))
    .thenReturn("user info fetching failed");

    Mockito.when(dao.getUserDetails(Mockito.anyInt()))
    .thenReturn(1);
    Mockito.when(dao.getClientDetails(Mockito.anyString()))
    .thenReturn(true);
    Mockito.when(userDao.getVehicleList(Mockito.anyInt()))
    .thenReturn(list);
    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericMessage(Mockito.anyString())).thenReturn("Add Vehicle Screen");
    service.getNextScreeninfo(clientBo);
  }

	@Test
	public void getNextScreeninfoTest3() throws RingException {
		List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
		ClientBo clientBo = new  ClientBo();
		clientBo.setClientSecret("RingAuto");
		clientBo.setClientId("dc45f4b1-5826-4b18-b5f8-edb413325cbd");
		clientBo.setUserId(-1);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user info fetching failed");

		Mockito.when(dao.getUserDetails(Mockito.anyInt()))
		.thenReturn(1);
		Mockito.when(dao.getClientDetails(Mockito.anyString()))
		.thenReturn(true);
		Mockito.when(userDao.getVehicleList(Mockito.anyInt()))
		.thenReturn(list);
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericMessage(Mockito.anyString())).thenReturn("Add Vehicle Screen");
		service.getNextScreeninfo(clientBo);
	}

	@Test
	public void getNextScreeninfoTest4() throws RingException {
		List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
		ClientBo clientBo = new  ClientBo();
		clientBo.setClientSecret("RingAuto");
		clientBo.setClientId("dc45f4b1-5826-4b18-b5f8-edb413325cbd");
		clientBo.setUserId(-1);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user info fetching failed");

		Mockito.when(dao.getUserDetails(Mockito.anyInt()))
		.thenReturn(1);
		Mockito.when(dao.getClientDetails(Mockito.anyString()))
		.thenReturn(false);
		Mockito.when(userDao.getVehicleList(Mockito.anyInt()))
		.thenReturn(list);
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericMessage(Mockito.anyString())).thenReturn("Add Vehicle Screen");
		service.getNextScreeninfo(clientBo);
	}


	@Test
	public void getNextScreeninfoTest5() throws RingException {
		List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
		ClientBo clientBo = new  ClientBo();
		clientBo.setClientSecret("RingAuto");
		clientBo.setClientId("dc45f4b1-5826-4b18-b5f8-edb413325cbd");
		clientBo.setUserId(1);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user info fetching failed");

		Mockito.when(dao.getUserDetails(Mockito.anyInt()))
		.thenReturn(1);
		Mockito.when(dao.getClientDetails(Mockito.anyString()))
		.thenReturn(true);
		Mockito.when(userDao.getVehicleList(Mockito.anyInt()))
		.thenReturn(list);
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericMessage(Mockito.anyString())).thenReturn("Home Screen");
		service.getNextScreeninfo(clientBo);
	}


}
