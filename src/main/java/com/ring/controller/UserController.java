package com.ring.controller;

import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.UserService;
import com.ring.util.ResponseUtil;
import com.ring.util.UserProfileBoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Controller class to manage and execute the flow of signup API. Uses
 * signupService interface and its implementation class for services.
 * </p>
 */
@RestController
@RequestMapping(UrlConstantsBo.USERS)
public class UserController {
  /**
   * Autowire SignUpService to use methods defined in SignUpService.
   */
  @Autowired
  UserService service;

  @Autowired
	UserProfileBoValidator validator;
	

	 /**
   * method used for registering user into app.
   *
   */
  @RequestMapping(
      method = RequestMethod.POST)
  public ResponseEntity<ResponseBo> registerUser(
      final @RequestBody UserProfileBo signUpBo)
          throws RingException {
    validator.validateForSignup(signUpBo);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userId", service.registerUser(signUpBo));
    return new ResponseEntity<>( ResponseUtil.genericResponseData
        (RingMessageCode.RM_2000.name(),map), HttpStatus.OK);

  }

  /**
   * method used for getting user info from app.
   *
   */
  @RequestMapping(
      value = UrlConstantsBo.USER_ID,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseDataBo> getUserInfo(
      @PathVariable("userId") int userId)
      throws RingException {
    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_2001.name(),
            service.getUserInfo(userId)),
        HttpStatus.OK);

  }
  
  
 

  /**
   * method used for update user info from app.
   *
   */
  @RequestMapping(
      value = UrlConstantsBo.USER_ID,
      method = RequestMethod.PUT)
  public ResponseEntity<ResponseBo> updateUserInfo(
      @PathVariable("userId") int userId,
      @RequestBody UserProfileBo userProfileBo)
      throws RingException {
    validator.validateForUpdate(userProfileBo);
    service.updateInfo(userId, userProfileBo);
    return new ResponseEntity<>(
        ResponseUtil.genericResponse(RingMessageCode.RM_2002.name()),
        HttpStatus.OK);

  }
}