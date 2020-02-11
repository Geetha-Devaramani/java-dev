package com.ring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ring.social.facebook")
public class FacebookConfig {
  private String appId;
  private String appSecret;
  private String validateTokenUrl;
  public String getAppId() {
    return appId;
  }
  public void setAppId(String appId) {
    this.appId = appId;
  }
  public String getAppSecret() {
    return appSecret;
  }
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }
  public String getValidateTokenUrl() {
    return validateTokenUrl;
  }
  public void setValidateTokenUrl(String validateTokenUrl) {
    this.validateTokenUrl = validateTokenUrl;
  }

}
