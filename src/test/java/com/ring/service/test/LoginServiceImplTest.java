package com.ring.service.test;

import static org.junit.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.UserProfileBo;
import com.ring.constants.LoginType;
import com.ring.dao.LoginDao;
import com.ring.service.LoginServiceImpl;

public class LoginServiceImplTest {

	@Mock
	LoginDao dao;

	@InjectMocks
	LoginServiceImpl service;

	@BeforeMethod(alwaysRun = true)
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loadUserByUsernameTest1() {
		UserProfileBo dataFromDb = new UserProfileBo();
		dataFromDb.setLoginId("xyz@gmail.com");
		dataFromDb.setLoginType(LoginType.APPLICATION);
		dataFromDb.setUserName("Reeth");
		dataFromDb.setPassword("Qwerty@1234");
		dataFromDb.setUserId(1);

		Mockito.when(dao.getUser("xyz@gmail.com")).thenReturn(dataFromDb);

		assertEquals("xyz@gmail.com", service.loadUserByUsername("xyz@gmail.com").getUsername());
	}

	@Test(expectedExceptions = UsernameNotFoundException.class)
	public void loadUserByUsernameTest2() {
		Mockito.when(dao.getUser("xyz@gmail.com")).thenThrow(new EmptyResultDataAccessException(1));
		service.loadUserByUsername("xyz@gmail.com");
	}

}
