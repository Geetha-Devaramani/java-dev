 package com.ring.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LoginType {

  APPLICATION("Application"), FACEBOOK("Facebook");

  private String loginType;

  private LoginType(String loginType) {
    this.loginType = loginType;
  }

  public String getloginType() {
    return loginType;
  }

  @JsonCreator
  public static LoginType fromLoginTypeString(
      String loginType) {

    for (LoginType loginTypeRequest : LoginType.values()) {

      if (loginTypeRequest.getloginType().equalsIgnoreCase(loginType)) {
        return loginTypeRequest;
      }

    }
    return null;

  }

}
