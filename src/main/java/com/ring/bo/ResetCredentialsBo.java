package com.ring.bo;

import com.ring.constants.TokenType;

public class ResetCredentialsBo extends UserBo {

  /**
   * 
   */
  private static final long serialVersionUID = -2139906988161751884L;
  
  Integer resetTokenId;
  
  String resetToken;

  String resetTokenExpTime;

  TokenType resetTokenType;

  public Integer getResetTokenId() {
    return resetTokenId;
  }

  public void setResetTokenId(Integer resetTokenId) {
    this.resetTokenId = resetTokenId;
  }

  public String getResetToken() {
    return resetToken;
  }

  public void setResetToken(String resetToken) {
    this.resetToken = resetToken;
  }

  public String getResetTokenExpTime() {
    return resetTokenExpTime;
  }

  public void setResetTokenExpTime(String resetTokenExpTime) {
    this.resetTokenExpTime = resetTokenExpTime;
  }

  public TokenType getResetTokenType() {
    return resetTokenType;
  }

  public void setResetTokenType(TokenType resetTokenType) {
    this.resetTokenType = resetTokenType;
  }

  

  

}
