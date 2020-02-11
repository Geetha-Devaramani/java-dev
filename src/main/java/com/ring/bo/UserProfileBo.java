package com.ring.bo;

import com.ring.constants.GenderType;
import com.ring.constants.LoginType;
import com.ring.constants.Status;

import java.sql.Timestamp;

public class UserProfileBo extends UserBo {

  /**
   * 
   */
  private static final long serialVersionUID = -8258413168222908092L;

  private LoginType loginType;
  private String email;
  private GenderType gender;
  private String dob;
  private String userName;
  private String phoneNumber;
  private Status status;
  private int userRole;
  private int createdUserId;
  private Timestamp createdDateTime;
  private int lastMOdifiedUserId;
  private Timestamp lastMOdifiedDateTime;

  public LoginType getLoginType() {
    return loginType;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setLoginType(LoginType loginType) {
    this.loginType = loginType;
  }
  
  public GenderType getGender() {
    return gender;
  }
  
 public void setGender(GenderType gender) {
    this.gender = gender;
  }
  /* public void setGender(String gender) {
    this.gender = GenderType.setGenderTypeUsingCustomInput(gender);
  }*/
  public String getDob() {
    return dob;
  }
  public void setDob(String dob) {
    this.dob = dob;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public int getUserRole() {
    return userRole;
  }
  public void setUserRole(int userRole) {
    this.userRole = userRole;
  }
  public int getCreatedUserId() {
    return createdUserId;
  }
  public void setCreatedUserId(int createdUserId) {
    this.createdUserId = createdUserId;
  }
  public Timestamp getCreatedDateTime() {
    return createdDateTime;
  }
  public void setCreatedDateTime(Timestamp createdDateTime) {
    this.createdDateTime = createdDateTime;
  }
  public int getLastMOdifiedUserId() {
    return lastMOdifiedUserId;
  }
  public void setLastMOdifiedUserId(int lastMOdifiedUserId) {
    this.lastMOdifiedUserId = lastMOdifiedUserId;
  }
  public Timestamp getLastMOdifiedDateTime() {
    return lastMOdifiedDateTime;
  }
  public void setLastMOdifiedDateTime(Timestamp lastMOdifiedDateTime) {
    this.lastMOdifiedDateTime = lastMOdifiedDateTime;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
