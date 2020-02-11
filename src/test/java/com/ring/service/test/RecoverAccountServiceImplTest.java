package com.ring.service.test;

import static org.testng.Assert.assertThrows;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.UserProfileBo;
import com.ring.dao.RecoverAccountDao;
import com.ring.exceptions.RingException;
import com.ring.service.EmailServiceImpl;
import com.ring.service.RecoverAccountServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RecoverAccountServiceImplTest {

  @Mock
  private RecoverAccountDao dao;

  @Mock
  private EmailServiceImpl emailService;

  @InjectMocks
  RecoverAccountServiceImpl service;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void sendMailTest1() throws RingException {
    UserProfileBo userInfoFromDb = new UserProfileBo();
    userInfoFromDb.setUserId(123);
    Mockito.when(dao.getUserInfoFromDb("xyz@gmail.com"))
        .thenReturn(userInfoFromDb);
    Mockito.doNothing().when(emailService).sendUpdatePasswordLink(
        Mockito.any(UserProfileBo.class), Mockito.anyString());
    Mockito.doNothing().when(dao).persistTokenInDb(Mockito.anyInt(),
        Mockito.anyString());
    service.sendMail("xyz@gmail.com");

  }

  @Test
  public void sendMailTest2() throws RingException {
    UserProfileBo userInfoFromDb = new UserProfileBo();
    userInfoFromDb.setUserId(123);
    Mockito.when(dao.getUserInfoFromDb("xyz@gmail.com"))
        .thenThrow(new EmptyResultDataAccessException(1));
    assertThrows(RingException.class, () -> service.sendMail("xyz@gmail.com"));

  }

  @Test
  public void resetCredentialsTest1() throws RingException {
    ResetCredentialsBo input = new ResetCredentialsBo();
    input.setPassword("7623bg65v75");
    input.setResetToken("78670h58v120b029b87");

    ResetCredentialsBo dataFromDb = new ResetCredentialsBo();
    dataFromDb.setResetToken("78670h58v120b029b87");
    dataFromDb.setResetTokenId(123);
    
    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd hh:mm:ss a z");
    dataFromDb.setResetTokenExpTime(formatter.format(ZonedDateTime.now()));

    Mockito.when(dao.getDataBasedOnToken(input.getResetToken()))
        .thenReturn(dataFromDb);

    Mockito.doNothing().when(dao).resetPassword(Mockito.anyString(),
        Mockito.any(ResetCredentialsBo.class));

    service.resetCredentials(input);

  }

  @Test
  public void resetCredentialsTest2() throws RingException {
    ResetCredentialsBo input = new ResetCredentialsBo();
    input.setPassword("7623bg65v75");
    input.setResetToken("78670h58v120b029b87");

    Mockito.when(dao.getDataBasedOnToken(input.getResetToken()))
        .thenThrow(new EmptyResultDataAccessException(1));

    assertThrows(RingException.class, () -> service.resetCredentials(input));

  }
  
  @Test
  public void resetCredentialsTest3() throws RingException {
    ResetCredentialsBo input = new ResetCredentialsBo();
    input.setPassword("7623bg65v75");
    input.setResetToken("78670h58v120b029b87");

    ResetCredentialsBo dataFromDb = new ResetCredentialsBo();
    dataFromDb.setResetToken("78670h58v120b029b87");
    dataFromDb.setResetTokenId(123);
    
    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss a z");
    dataFromDb.setResetTokenExpTime(formatter.format(ZonedDateTime.now()));
    dataFromDb.setResetTokenExpTime("2018-10-30 08:59:16 AM IST");

    Mockito.when(dao.getDataBasedOnToken(input.getResetToken()))
        .thenReturn(dataFromDb);

    Mockito.doNothing().when(dao).resetPassword(Mockito.anyString(),
        Mockito.any(ResetCredentialsBo.class));

    assertThrows(RingException.class, () -> service.resetCredentials(input));

  }

}
