package com.ring.controller;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.RecoverAccountService;
import com.ring.util.ResponseUtil;
import com.ring.util.UserProfileBoValidator;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(UrlConstantsBo.APPLICATION)
public class RecoverAccountController {
  
  @Autowired
  private RecoverAccountService recoverAccountService;

  @Autowired
  private UserProfileBoValidator userProfileBoValidator;
  
  @RequestMapping(
      value = UrlConstantsBo.RECOVER_ACCOUNT,
      method = RequestMethod.POST)
  public ResponseEntity<ResponseBo> recoverAccountHandler(
      final @RequestBody UserProfileBo resetPasswordBo)
      throws RingException {
    userProfileBoValidator.validateForForgotPassword(resetPasswordBo);
    recoverAccountService.sendMail(resetPasswordBo.getEmail());
    return new ResponseEntity<>(
        ResponseUtil.genericResponse(
            RingMessageCode.RM_1002.name()),
        HttpStatus.OK);

  }
  
  @RequestMapping(
      value = UrlConstantsBo.RESET_CREDENTIALS,
      method = RequestMethod.PUT)
  public ResponseEntity<ResponseBo> resetCredentialsHandler(
      final @RequestBody ResetCredentialsBo resetPasswordBo)
      throws RingException {
    userProfileBoValidator.validateForResetCredentials(resetPasswordBo);
    recoverAccountService.resetCredentials(resetPasswordBo);
    return new ResponseEntity<>(
        ResponseUtil.genericResponse(
            RingMessageCode.RM_1004.name()),
        HttpStatus.OK);

  }
  

}
