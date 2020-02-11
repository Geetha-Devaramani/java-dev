package com.ring.service.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.ClientBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.GenderType;
import com.ring.constants.LoginType;
import com.ring.dao.UserDao;
import com.ring.dao.VehicleDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.service.UserServiceImpl;


public class UserServiceImplTest  {

	@Mock
	UserDao dao;

	@Mock
	VehicleDao userDao;

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
	public void getUserInfoTest1() throws RingException {
		Map<String, Object> userInfoMap = new HashMap<>();
		userInfoMap.put("userName", "Reeth");
		userInfoMap.put("email", "xyz@gmail.com");
		userInfoMap.put("loginType", LoginType.APPLICATION.getloginType());

		Mockito.when(dao.getUserInfo(123)).thenReturn(userInfoMap);

		Map<String, Object> dataFromMethod = (Map<String, Object>) service
				.getUserInfo(123);

		assertEquals("Reeth", dataFromMethod.get("userName"));
		assertEquals("xyz@gmail.com", dataFromMethod.get("email"));
	}

	@Test(
			expectedExceptions = RingException.class)
	public void getUserInfoTest2() throws RingException {
		Mockito.when(dao.getUserInfo(123))
		.thenThrow(new EmptyResultDataAccessException(1));
		service.getUserInfo(123);

	}

	@Test
	public void registerClientTest1() {
		ClientBo clientBo = new ClientBo();
		clientBo.setClientId("3636y3747m7");
		clientBo.setClientSecret("Secret");

		Mockito.doNothing().when(dao).registerClient(clientBo);

		service.registerClient(clientBo);

	}

	@Test
	public void updateInfoTest1() throws RingException {
		UserProfileBo userInfo = new UserProfileBo();
		userInfo.setGender(GenderType.FEMALE);
		userInfo.setEmail("xyz@gmail.com");

		Mockito.when(dao.updateInfo(Mockito.anyInt(),
				Mockito.any(UserProfileBo.class), Mockito.anyString())).thenReturn(1);

		service.updateInfo(1, userInfo);

	}

	@Test(
			expectedExceptions = RingException.class)
	public void updateInfoTest2() throws RingException {
		UserProfileBo userInfo = new UserProfileBo();
		userInfo.setGender(GenderType.FEMALE);
		userInfo.setEmail("xyz@gmail.com");

		Mockito.when(dao.updateInfo(Mockito.anyInt(),
				Mockito.any(UserProfileBo.class), Mockito.anyString())).thenReturn(0);

		service.updateInfo(1, userInfo);

	}

	@Test
	public void registerUserTest1() throws RingException {
		UserProfileBo userInfo = new UserProfileBo();
		userInfo.setLoginId("xyz@gmail.com");
		userInfo.setPassword("Qwerty@1234");
		userInfo.setUserName("XYZ");

		Mockito.when(dao.registerUser(userInfo)).thenReturn(1234);

		assertEquals(1234, service.registerUser(userInfo));
	}

	@Test(
			expectedExceptions = RingException.class)
	public void registerUserTest2() throws RingException {
		UserProfileBo userInfo = new UserProfileBo();
		userInfo.setLoginId("xyz@gmail.com");
		userInfo.setPassword("Qwerty@1234");
		userInfo.setUserName("XYZ");

		Mockito.when(dao.registerUser(userInfo)).thenThrow(
				new RingException(RingErrorCode.RA_1000, RingResponseCode.OK));

		service.registerUser(userInfo);
	}


@Test 
public void getQuatersTest() throws RingException {
  service.getQuaters();
}

}
