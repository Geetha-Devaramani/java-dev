package com.ring.bo;

public class ClientBo extends UserBo{

  /**
   * 
   */
  private static final long serialVersionUID = -5982765263350291048L;
  private String clientId;
  private String clientSecret;

  public String getClientId() {
    return clientId;
  }
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
  public String getClientSecret() {
    return clientSecret;
  }
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }



}