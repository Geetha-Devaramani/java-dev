package com.ring.constants;

public enum TokenType {

  EMAIL("EMAIL");

  String tokenType;

  private TokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getTokenType() {
    return tokenType;
  }

  /**
   * Get token Type.
   * 
   * @param type token.
   * @return token type.
   */
  public static TokenType getTokenType(
      String type) {

    for (TokenType tokenValue : TokenType.values()) {

      if (tokenValue.getTokenType().equalsIgnoreCase(type)) {
        return tokenValue;
      }

    }
    return null;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

}
