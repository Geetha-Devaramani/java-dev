package com.ring.oauth;

import com.ring.bo.UserProfileBo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class OAuthUser
    extends
      org.springframework.security.core.userdetails.User {

  private static final long serialVersionUID = -7440607032976318411L;

  private UserProfileBo userDetails;

  /**
   * Constructor - initialize oath user.
   * 
   * @param user oauth user.
   */

  public OAuthUser(UserProfileBo user) {
    super(user.getLoginId(),
        user.getPassword(), true, true, true, true,
        getAuthority());
    this.userDetails = user;

  }

  public UserProfileBo getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(UserProfileBo userDetails) {
    this.userDetails = userDetails;
  }

  private static Collection<? extends GrantedAuthority> getAuthority() {
    return Arrays.asList(new SimpleGrantedAuthority("USER"));
  }

}
