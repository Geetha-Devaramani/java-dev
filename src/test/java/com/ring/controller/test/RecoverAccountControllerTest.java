package com.ring.controller.test;

import static org.testng.Assert.assertEquals;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.UserProfileBo;
import com.ring.controller.RecoverAccountController;
import com.ring.exceptions.RingException;
import com.ring.service.RecoverAccountService;
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
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

@PrepareForTest({ResponseUtil.class})
@PowerMockIgnore("javax.management.*")
public class RecoverAccountControllerTest extends PowerMockTestCase {

  @Mock
  private RecoverAccountService recoverAccountService;

  @Mock
  private UserProfileBoValidator userProfileBoValidator;

  @InjectMocks
  RecoverAccountController controller;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  ResponseBo responseBo = new ResponseBo();

  @Test
  public void recoverAccountHandlerTest1() throws RingException {
    UserProfileBo profileBo = new UserProfileBo();
    profileBo.setEmail("xyz@gmail.com");
    Mockito.doNothing().when(userProfileBoValidator)
        .validateForForgotPassword(profileBo);
    Mockito.doNothing().when(recoverAccountService)
        .sendMail(profileBo.getEmail());

    responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseBo.setMessage("email sent successfully");

    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericResponse(Mockito.anyString()))
        .thenReturn(responseBo);

    ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
        responseBo, HttpStatus.OK);

    assertEquals(response.toString(),
        controller.recoverAccountHandler(profileBo).toString());

  }

  @Test
  public void resetCredentialsHandlerTest() throws RingException {
    ResetCredentialsBo resetCredentialsBo = new ResetCredentialsBo();
    Mockito.doNothing().when(userProfileBoValidator)
        .validateForResetCredentials(resetCredentialsBo);
    Mockito.doNothing().when(recoverAccountService)
        .resetCredentials(resetCredentialsBo);
    responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseBo.setMessage("Password changed successfully");

    PowerMockito.mockStatic(ResponseUtil.class);
    Mockito.when(ResponseUtil.genericResponse(Mockito.anyString()))
        .thenReturn(responseBo);

    ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
        responseBo, HttpStatus.OK);

    assertEquals(response.toString(),
        controller.resetCredentialsHandler(resetCredentialsBo).toString());
  }

}
