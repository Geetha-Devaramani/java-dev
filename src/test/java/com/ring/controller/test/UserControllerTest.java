package com.ring.controller.test;

import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.bo.UserProfileBo;
import com.ring.controller.UserController;
import com.ring.service.UserService;
import com.ring.util.ResponseUtil;
import com.ring.util.UserProfileBoValidator;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@PrepareForTest({ResponseUtil.class})
@PowerMockIgnore("javax.management.*")
public class UserControllerTest extends PowerMockTestCase {

	@Mock
	UserService service;

	@Mock
	UserProfileBoValidator validator;

	@Mock
	Properties prop;

	@InjectMocks
	UserController controller;

	@BeforeMethod(
			alwaysRun = true)
	public void initMocks() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	ResponseDataBo responseDataBo = new ResponseDataBo();
	ResponseBo responseBo = new ResponseBo();

	@Test
	public void registerUserTest1() throws Exception {
		int userId= 1;
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user sing up failed");

		Mockito.doNothing().when(validator).validateForSignup(Mockito.any(UserProfileBo.class));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		Mockito
		.when(service.registerUser( Mockito.any(UserProfileBo.class))).thenReturn(userId);
		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("Signup successfull");
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);
		controller.registerUser( new UserProfileBo()).toString();
	}

	@Test
	public void updateUserInfoTest1() throws Exception {
		int userId= 1;
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user update failed");

		Mockito.doNothing().when(validator).validateForUpdate(Mockito.any(UserProfileBo.class));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		Mockito.doNothing().when(service).updateInfo(Mockito.anyInt(),Mockito.any(UserProfileBo.class));
		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("User details updated successfully");
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);
		controller.updateUserInfo(1,new UserProfileBo()).toString();
	}

	@Test
	public void getUserInfoTest1() throws Exception {
		int userId= 1;
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("user info fetching failed");
		UserProfileBo user = new UserProfileBo();
		user.setUserName("ravi");
		
		Mockito
		.when(service.getUserInfo( Mockito.anyInt())).thenReturn(user);
		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("User details fetched successfully");
		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);
		controller.getUserInfo(1).toString();
	}
	
}
