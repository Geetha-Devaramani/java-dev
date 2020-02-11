package com.ring.service.test;

import static org.testng.Assert.assertEquals;

import com.ring.bo.SocialLoginBo;
import com.ring.config.UrlProvider;
import com.ring.constants.LoginType;
import com.ring.dao.UserDao;
import com.ring.exceptions.RingException;
import com.ring.service.FacebookServiceProvider;
import com.ring.service.SocialLoginServiceImplementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SocialLoginServiceImplementationTest {

  @Mock
  UserDao dao;

  @Mock
  FacebookServiceProvider fbServiceProvider;

  @Mock
  private RestTemplate restTemplate = new RestTemplate();

  @Mock
  private UrlProvider urlProvider;

  @InjectMocks
  SocialLoginServiceImplementation service;
  
  @BeforeMethod(alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void loginUsingFacebookTest1() throws RingException {
    SocialLoginBo loginBo = new SocialLoginBo();
    loginBo.setAccessToken("qwertyAEGAGRA57ifxnmfxm1234");
    loginBo.setLoginType(LoginType.FACEBOOK);
    loginBo.setUserName("Reeth");
    loginBo.setEmail("xyz@gmail.com");
    loginBo.setLoginId("121247823423589895");
    
    Mockito.when(fbServiceProvider.validateAccessToken(Mockito.anyString())).thenReturn(true);
    Mockito.doNothing().when(dao).registerSocialUser(loginBo);
    Mockito.when(urlProvider.getLoginUrl()).thenReturn("http://.......");
    
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("loginType", "FACEBOOK");
    ResponseEntity<Map> response = new ResponseEntity<Map>(responseMap, HttpStatus.OK);
    
    Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class)
        ,Mockito.any(HttpEntity.class),Mockito.any(Class.class))).thenReturn(response);
    
    assertEquals(response.toString(), service.loginUsingFacebook(loginBo).toString());
    
  }
  
  @Test(expectedExceptions = RingException.class)
  public void loginUsingFacebookTest2() throws RingException {
    SocialLoginBo loginBo = new SocialLoginBo();
    loginBo.setAccessToken("qwertyAEGAGRA57ifxnmfxm1234");
    loginBo.setLoginType(LoginType.FACEBOOK);
    loginBo.setUserName("Reeth");
    loginBo.setEmail("xyz@gmail.com");
    loginBo.setLoginId("121247823423589895");
    
    Mockito.when(fbServiceProvider.validateAccessToken(Mockito.anyString())).thenReturn(false);
    service.loginUsingFacebook(loginBo).toString();
    
  }

}
