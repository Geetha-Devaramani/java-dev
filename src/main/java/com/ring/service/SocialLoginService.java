package com.ring.service;

import com.ring.bo.SocialLoginBo;
import com.ring.exceptions.RingException;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface SocialLoginService {

  public ResponseEntity<Map> loginUsingFacebook(SocialLoginBo socialUserBo)
      throws RingException;

}
