package com.ring.service;

import com.ring.bo.UserProfileBo;
import com.ring.dao.LoginDao;
import com.ring.oauth.OAuthUser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements UserDetailsService {

  private static final Logger LOGGER = LogManager
      .getLogger(LoginServiceImpl.class);

  @Autowired
  LoginDao loginDao;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserProfileBo user = null;
    try {
      user = loginDao.getUser(username);
    } catch (EmptyResultDataAccessException ex) {
      LOGGER.error("Username with Id:" + username + " not found in database");
      throw new UsernameNotFoundException("User not found");
    }
    return new OAuthUser(user);

  }

}
