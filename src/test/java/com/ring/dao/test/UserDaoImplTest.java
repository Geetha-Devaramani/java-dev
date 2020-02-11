package com.ring.dao.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import com.ring.bo.ClientBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.GenderType;
import com.ring.constants.LoginType;
import com.ring.dao.UserDaoImpl;
import com.ring.exceptions.RingException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@PrepareForTest({UserDaoImpl.class})
@PowerMockIgnore("javax.management.*")
public class UserDaoImplTest extends PowerMockTestCase {

  @Mock
  JdbcTemplate jdbctemplate;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  Connection con;

  @Mock
  PreparedStatement ps;

  @Mock
  GeneratedKeyHolder keyHolder;

  @InjectMocks
  UserDaoImpl dao;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getUserInfoTest() {
    Map<String, Object> userInfoMap = new HashMap<>();
    userInfoMap.put("userName", "Reeth");
    userInfoMap.put("email", "xyz@gmail.com");
    userInfoMap.put("loginType", LoginType.APPLICATION.getloginType());

    Mockito
        .when(jdbctemplate.queryForMap(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(userInfoMap);

    Map<String, Object> dataFromMethod = (Map<String, Object>) dao
        .getUserInfo(123);

    assertEquals("Reeth", dataFromMethod.get("userName"));
    assertEquals("xyz@gmail.com", dataFromMethod.get("email"));

  }

  @Test
  public void registerClientTest1() {
    ClientBo clientBo = new ClientBo();
    clientBo.setClientId("3636y3747m7");
    clientBo.setClientSecret("Secret");

    Mockito.when(
        jdbctemplate.update(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(1);

    dao.registerClient(clientBo);
  }

  @Test
  public void updateInfoTest1() {
    UserProfileBo userInfo = new UserProfileBo();
    userInfo.setGender(GenderType.FEMALE);
    userInfo.setEmail("xyz@gmail.com");
    userInfo.setUserName("Reeth");

    Mockito.when(
        jdbctemplate.update(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(1);

    dao.updateInfo(1, userInfo, "query");

  }

  @Test
  public void registerUserTest1() throws Exception {
    UserProfileBo userInfo = new UserProfileBo();
    userInfo.setLoginId("xyz@gmail.com");
    userInfo.setPassword("Qwerty@1234");
    userInfo.setUserName("XYZ");

    Mockito.when(passwordEncoder.encode(userInfo.getPassword()))
        .thenReturn("897sdfgha897435338994589s8345h");

    Mockito
        .when(jdbctemplate.update(Mockito.any(PreparedStatementCreator.class),
            Mockito.any(KeyHolder.class)))
        .thenAnswer(new Answer<Number>() {

          @Override
          public Number answer(InvocationOnMock invocation)
              throws Throwable {
            Object[] args = invocation.getArguments();
            PreparedStatementCreator arg = (PreparedStatementCreator) args[0];
            arg.createPreparedStatement(con);
            return 123;
          }
        });

    Mockito.when(con.prepareStatement(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(ps);
    Mockito.doNothing().when(ps).setString(Mockito.anyInt(),
        Mockito.anyString());
    Mockito.doNothing().when(ps).setInt(Mockito.anyInt(), Mockito.anyInt());

    Map<String, Object> mapOfKeys = new HashMap<>();
    mapOfKeys.put("usr_user_id", 123);

    PowerMockito.whenNew(GeneratedKeyHolder.class).withNoArguments()
        .thenReturn(keyHolder);
    Mockito.when(keyHolder.getKeys()).thenReturn(mapOfKeys);

    assertEquals(123, dao.registerUser(userInfo));

  }
  
  @Test
  public void registerUserTest2() throws Exception {
    UserProfileBo userInfo = new UserProfileBo();
    userInfo.setLoginId("xyz@gmail.com");
    userInfo.setPassword("Qwerty@1234");
    userInfo.setUserName("XYZ");

    Mockito.when(passwordEncoder.encode(userInfo.getPassword()))
        .thenReturn("897sdfgha897435338994589s8345h");

    Mockito
        .when(jdbctemplate.update(Mockito.any(PreparedStatementCreator.class),
            Mockito.any(KeyHolder.class))).thenThrow(new DuplicateKeyException("Duplicate Key"));
    assertThrows(RingException.class,() -> dao.registerUser(userInfo));

  }

}
