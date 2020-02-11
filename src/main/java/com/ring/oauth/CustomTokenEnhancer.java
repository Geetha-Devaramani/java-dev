package com.ring.oauth;

import com.ring.bo.UserProfileBo;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    final Map<String, Object> additionalInfo = new HashMap<>();
    OAuthUser authUser = (OAuthUser) authentication.getPrincipal();
    final UserProfileBo userInfo = authUser.getUserDetails();
    additionalInfo.put("code", 200);
    additionalInfo.put("message", "Login Successful");
    additionalInfo.put("userId", userInfo.getUserId());
    additionalInfo.put("loginType", userInfo.getLoginType());
    additionalInfo.put("userName", userInfo.getUserName());
    ((DefaultOAuth2AccessToken) accessToken)
        .setAdditionalInformation(additionalInfo);
    return accessToken;
  }

}