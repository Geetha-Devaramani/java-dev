package com.ring.bo;

public class SocialLoginBo extends UserProfileBo {

  /**
   * 
   */
  private static final long serialVersionUID = -1755294492707807325L;
  
  private String accessToken;
  private String basicAuthString;
  
  public String getAccessToken() {
    return accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  public String getBasicAuthString() {
    return basicAuthString;
  }
  public void setBasicAuthString(String basicAuthString) {
    this.basicAuthString = basicAuthString;
  }


}
