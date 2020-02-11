package com.ring.dao.test;

import static org.testng.Assert.assertEquals;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.LoginType;
import com.ring.dao.RecoverAccountDaoImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RecoverAccountDaoTest {

  @Mock
  private JdbcTemplate template;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  RecoverAccountDaoImpl dao;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void persistTokenInDbTest1() {
    Mockito
        .when(template.update(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(1);
    dao.persistTokenInDb(123, "7yd8937623046290");
  }

  @Test
  public void resetPasswordTest1() {
    ResetCredentialsBo input = new ResetCredentialsBo();
    input.setResetTokenId(123);
    Mockito.when(passwordEncoder.encode(Mockito.anyString()))
        .thenReturn("789263940782");
    Mockito
        .when(template.update(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(1);
    dao.resetPassword("b92v78529", input);
  }

  @Test
  public void getUserInfoFromDbTest1() {
    Map<String, Object> map = new HashMap<>();
    map.put("userName", "Reeth");
    map.put("userId", 123);
    map.put("loginType", LoginType.APPLICATION.getloginType());
    
    Mockito.when(template.queryForMap(Mockito.anyString(), Mockito.any(Object[].class))).thenReturn(map);
    
    UserProfileBo bo = dao.getUserInfoFromDb("xyz@gmail.com");
    
    assertEquals("Reeth", bo.getUserName());
    
   }
  
  @Test
  public void getDataBasedOnTokenTest1() {
    Map<String, Object> map = new HashMap<>();
    map.put("resetToken", "763952562b5vn2589");
    map.put("userId", 123);
    map.put("resetTokenId", 1234);
    
    Mockito.when(template.queryForMap(Mockito.anyString(), Mockito.any(Object[].class))).thenReturn(map);
    
    ResetCredentialsBo bo = dao.getDataBasedOnToken("763952562b5vn2589");
    
    assertEquals("763952562b5vn2589", bo.getResetToken());
    
   }

}
