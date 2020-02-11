package com.ring.bo;

public class UserBo extends BaseBo{

  /**
   * 
   */
  private static final long serialVersionUID = -3968672872084150316L;
  
  private Integer userId;
  private String loginId;
  private String password;
  
  public Integer getUserId() {
    return userId;
  }
  
  public void setUserId(Integer userId) {
    this.userId = userId;
  }
 
  public String getLoginId() {
    return loginId;
  }
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  

}
