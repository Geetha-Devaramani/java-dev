package com.ring.controller;

import com.ring.bo.ClientBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.SocialLoginBo;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.DocumentService;
import com.ring.service.SocialLoginService;
import com.ring.service.UserService;
import com.ring.util.ClientBoValidator;
import com.ring.util.ResponseUtil;
import com.ring.util.UserProfileBoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(UrlConstantsBo.APPLICATION)
public class AppCoreController {

  @Autowired
  private SocialLoginService socialLoginService;

  @Autowired
  private UserService registerService;

  @Autowired
  private ConsumerTokenServices consumerTokenServices;

  @Autowired
  private UserProfileBoValidator userProfileBoValidator;

  @Autowired
  private ClientBoValidator clientBoValidator;

  @Autowired
  private DocumentService documentService;

  @RequestMapping(
      value = UrlConstantsBo.CLIENTS,
      method = RequestMethod.POST)
  public ResponseEntity<ResponseBo> registerClient(
      @RequestBody ClientBo clientBo)
      throws RingException {
    clientBoValidator.validateForClientAddition(clientBo);
    registerService.registerClient(clientBo);
    return new ResponseEntity<>(
        ResponseUtil.genericResponse(RingMessageCode.RM_1000.name()),
        HttpStatus.OK);
  }

  @RequestMapping(
      value = UrlConstantsBo.SOCIAL_LOGIN,
      method = RequestMethod.POST)
  public Object loginUsingSocialNetwork(@RequestBody SocialLoginBo socialUserBo,
      HttpServletRequest request)
      throws Exception {
    userProfileBoValidator.validateForSocialLogin(socialUserBo);
    ResponseEntity<Map> obj = null;
    socialUserBo.setBasicAuthString(
        request.getHeader("Authorization").replace("Basic", "")
            .replace("basic", "")
            .trim());
    if (socialUserBo.getLoginType().getloginType()
        .equalsIgnoreCase("facebook")) {
      obj = socialLoginService.loginUsingFacebook(socialUserBo);
    }
    return obj;

  }

  /**
   * Logout user - remove access token from token store.
   * 
   * @param request http request.
   * @return success or error message.
   */
  @RequestMapping(
      value = UrlConstantsBo.LOGOUT,
      method = RequestMethod.POST)
  public ResponseEntity<ResponseBo> logout(HttpServletRequest request)
      throws RingException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      String tokenValue = authHeader.replace("Bearer", "")
          .replace("bearer", "")
          .trim();
      consumerTokenServices.revokeToken(tokenValue);
    }
    return new ResponseEntity<>(
        ResponseUtil.genericResponse(RingMessageCode.RM_1001.name()),
        HttpStatus.OK);
  }

  /**
   * Screen info - get next screen info.
   * 
   * @param request ClientBo request.
   * @return success or error message.
   */
  @RequestMapping(
      value = UrlConstantsBo.SCREEN,
      method = RequestMethod.POST)
  public ResponseEntity<ResponseBo> screenDetails(
      @RequestBody ClientBo clientBo)
      throws RingException {
    clientBoValidator.validateForNextScreenInfo(clientBo);
    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_1003.name(),
            registerService.getNextScreeninfo(clientBo)),
        HttpStatus.OK);
  }

  /**
   * Quater info - get Quater info.
   * 
   * @return success or error message.
   */
  @RequestMapping(
      value = UrlConstantsBo.QUATER,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseBo> quaterDetails()
      throws RingException {

    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_7000.name(),
            registerService.getQuaters()),
        HttpStatus.OK);
  }

  /**
   * DOCUMENT_LIST info - get document info.
   * 
   * @return success or error message.
   */
  @RequestMapping(
      value = UrlConstantsBo.DOCUMENT_LIST,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseBo> documentDetails()
      throws RingException {

    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_8000.name(),
            documentService.getListOfDocument()),
        HttpStatus.OK);
  }

}
