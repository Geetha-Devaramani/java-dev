package com.ring.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class NextScreenInformationBo extends BaseBo{

  /**
   * 
   */
  private static final long serialVersionUID = -2078044162640293046L;


  private int code;
  private String screenMessage;
  @Value("${ring.helpUrl}")
  private String helpUrl;

  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }

  public String getScreenMessage() {
    return screenMessage;
  }
  public void setScreenMessage(String screenMessage) {
    this.screenMessage = screenMessage;
  }
  public String getHelpUrl() {
    return helpUrl;
  }
  public void setHelpUrl(String helpUrl) {
    this.helpUrl = helpUrl;
  }

}
