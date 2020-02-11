package com.ring.service;

import com.ring.config.FacebookConfig;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Map;

@Service
public class FacebookServiceProvider {

  @Autowired
  private FacebookConfig config;

  private RestTemplate restTemplate = new RestTemplate();

  public boolean validateAccessToken(String accessToken) throws RingException {
    Map<String, Object> getResponse = getAccesstokenInformation(accessToken);
    return (boolean) ((Map<String, Object>) getResponse.get("data"))
        .get("is_valid");
  }

  private Map<String, Object> getAccesstokenInformation(String accessToken)
      throws RingException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("accept", "application/json");
    return restTemplate.getForObject(getDebugTokenUrl(accessToken), Map.class,
        headers);

  }

  public String getDebugTokenUrl(String userToken) throws RingException {
    String fbAppAccessTokenUrl = null;
    try {
      fbAppAccessTokenUrl = String.format(
          config.getValidateTokenUrl(),
          URLEncoder.encode(userToken, "UTF-8"), URLEncoder
              .encode(
                  config.getAppId() + "|" + config.getAppSecret(),
                  "UTF-8"));
    } catch (Exception ex) {
      throw new RingException(RingErrorCode.RA_1000, RingResponseCode.OK);
    }
    return fbAppAccessTokenUrl;
  }

}
