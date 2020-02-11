package com.ring.dao.test;

import com.ring.bo.SocialLoginBo;
import com.ring.constants.LoginType;
import com.ring.dao.UserDaoImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SocialLoginDaoTest {

  @Mock
  JdbcTemplate jdbctemplate;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserDaoImpl dao;
  
  @BeforeMethod(alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void registerSocialUserTest1() {
    SocialLoginBo loginBo = new SocialLoginBo();
    loginBo.setAccessToken("qwertyAEGAGRA57ifxnmfxm1234");
    loginBo.setLoginType(LoginType.FACEBOOK);
    loginBo.setUserName("Reeth");
    loginBo.setEmail("xyz@gmail.com");
    loginBo.setLoginId("121247823423589895");
    
    Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("89qw7348952895v2890hrr7");
    Mockito.when(jdbctemplate.update(Mockito.anyString(), Mockito.any(Object[].class))).thenReturn(1);
    dao.registerSocialUser(loginBo);

  }
  @Test
  public void registerSocialUserTest2() {
    SocialLoginBo loginBo = new SocialLoginBo();
    loginBo.setAccessToken("qwertyAEGAGRA57ifxnmfxm1234");
    loginBo.setLoginType(LoginType.FACEBOOK);
    loginBo.setUserName("Reeth");
    loginBo.setLoginId("121247823423589895");
    
    Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("89qw7348952895v2890hrr7");
    Mockito.when(jdbctemplate.update(Mockito.anyString(), Mockito.any(Object[].class))).thenReturn(1);
    dao.registerSocialUser(loginBo);

  }
}
