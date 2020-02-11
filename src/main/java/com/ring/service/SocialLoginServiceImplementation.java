package com.ring.service;

import com.ring.bo.SocialLoginBo;
import com.ring.config.UrlProvider;
import com.ring.dao.UserDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SocialLoginServiceImplementation implements SocialLoginService {

  @Autowired
  UserDao dao;

  @Autowired
  FacebookServiceProvider fbServiceProvider;

  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private UrlProvider urlProvider;

  @Override
  public ResponseEntity<Map> loginUsingFacebook(SocialLoginBo socialUserBo)
      throws RingException {
    if (fbServiceProvider.validateAccessToken(socialUserBo.getAccessToken())) {
      dao.registerSocialUser(socialUserBo);
      return registerInAuthenticationServer(socialUserBo);
    }
    throw new RingException(RingErrorCode.RA_1000, RingResponseCode.OK);
  }

  private ResponseEntity<Map> registerInAuthenticationServer(
      SocialLoginBo socialUserBo) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Basic " + socialUserBo.getBasicAuthString());

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("username", socialUserBo.getLoginId());
    body.add("password", "RingAuto_" + socialUserBo.getLoginId());
    body.add("grant_type", "password");

    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body,
        headers);
    return restTemplate.exchange(urlProvider.getLoginUrl(), HttpMethod.POST,
        entity,
        Map.class);

  }

}
