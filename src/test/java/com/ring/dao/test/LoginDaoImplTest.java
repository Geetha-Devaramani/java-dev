package com.ring.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.UserProfileBo;
import com.ring.constants.LoginType;
import com.ring.dao.LoginDaoImpl;

public class LoginDaoImplTest {
	
	@Mock
	JdbcTemplate template;
	
	@InjectMocks
	LoginDaoImpl dao;
	
	@BeforeMethod(alwaysRun = true)
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUserTest1() {
		UserProfileBo dataFromDb1 = new UserProfileBo();
		dataFromDb1.setLoginId("xyz@gmail.com");
		dataFromDb1.setLoginType(LoginType.APPLICATION);
		dataFromDb1.setUserName("Reeth");
		dataFromDb1.setPassword("Qwerty@1234");
		dataFromDb1.setUserId(1);
		
		Map<String,Object> dataFromDb = new HashMap<>();
		dataFromDb.put("loginId", "xyz@gmail.com");
		
		Mockito.when(template.queryForMap(Mockito.anyString(),Mockito.anyString())).thenReturn(dataFromDb);
		
		assertEquals("xyz@gmail.com",dao.getUser("xyz@gmail.com").getLoginId());
		
		
	}

}
