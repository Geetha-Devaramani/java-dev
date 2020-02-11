package com.ring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ring.url")
public class UrlProvider {
  private String loginUrl;
  private String forgotPasswordUrl;
  private String reportIncidentUrl;
  public String getReportIncidentUrl() {
    return reportIncidentUrl;
  }
  public void setReportIncidentUrl(String reportIncidentUrl) {
    this.reportIncidentUrl = reportIncidentUrl;
  }
  public String getLoginUrl() {
    return loginUrl;
  }
  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }
  public String getForgotPasswordUrl() {
    return forgotPasswordUrl;
  }
  public void setForgotPasswordUrl(String forgotPasswordUrl) {
    this.forgotPasswordUrl = forgotPasswordUrl;
  }

}
