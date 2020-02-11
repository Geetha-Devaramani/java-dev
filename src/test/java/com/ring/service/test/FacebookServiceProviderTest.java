package com.ring.service.test;

import static org.testng.Assert.assertEquals;

import com.ring.config.FacebookConfig;
import com.ring.exceptions.RingException;
import com.ring.service.FacebookServiceProvider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class FacebookServiceProviderTest {

  @Mock
  private FacebookConfig config;

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  FacebookServiceProvider service;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void validateAccessTokenTest1() throws RingException {
    Mockito.when(config.getValidateTokenUrl()).thenReturn("http://......");
    Mockito.when(config.getAppId()).thenReturn("12144125");
    Mockito.when(config.getAppSecret()).thenReturn("jihasdfuigfasiu237846278");

    Map<String, Object> innerDataMap = new HashMap<>();
    innerDataMap.put("is_valid", true);
    Map<String, Object> outerDataMap = new HashMap<>();
    outerDataMap.put("data", innerDataMap);
    Mockito
        .when(restTemplate.getForObject(Mockito.anyString(),
            Mockito.any(Class.class), Mockito.any(HttpHeaders.class)))
        .thenReturn(outerDataMap);
    
    assertEquals(true,service.validateAccessToken("uioygfpu9wyg880734rajkf"));
  }
  
  @Test(expectedExceptions = RingException.class)
  public void validateAccessTokenTest2() throws RingException {
    Mockito.when(config.getValidateTokenUrl()).thenReturn(null);
    Mockito.when(config.getAppId()).thenReturn("12144125");
    Mockito.when(config.getAppSecret()).thenReturn("jihasdfuigfasiu237846278");

    Map<String, Object> innerDataMap = new HashMap<>();
    innerDataMap.put("is_valid", true);
    Map<String, Object> outerDataMap = new HashMap<>();
    outerDataMap.put("data", innerDataMap);
    Mockito
        .when(restTemplate.getForObject(Mockito.anyString(),
            Mockito.any(Class.class), Mockito.any(HttpHeaders.class)))
        .thenReturn(outerDataMap);
    
    service.validateAccessToken("uioygfpu9wyg880734rajkf");
  }
  

}
