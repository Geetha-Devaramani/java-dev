package com.ring.util;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.SocialLoginBo;
import com.ring.bo.UserProfileBo;
import com.ring.exceptions.RingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserProfileBoValidator {

  @Autowired
  UserInfoValidator validator;

  public void validateForSignup(UserProfileBo profileBo) throws RingException {
    validator.validateEmail(profileBo.getLoginId());
    validator.validatePassword(profileBo.getPassword());
    validator.validateUserName(profileBo.getUserName());

  }

  public void validateForUpdate(UserProfileBo profileBo) throws RingException {
    validator.validateUserName(profileBo.getUserName());
    if (!Objects.isNull(profileBo.getEmail())) {
      validator.validateEmail(profileBo.getEmail());
    }
    if (!Objects.isNull(profileBo.getPhoneNumber())) {
      validator.validatePhoneNumber(profileBo.getPhoneNumber());
    }
  }

  public void validateForForgotPassword(UserProfileBo profileBo)
      throws RingException {
    validator.validateEmail(profileBo.getEmail());
  }

  public void validateForSocialLogin(SocialLoginBo socialLoginBo)
      throws RingException {
    if (!Objects.isNull(socialLoginBo.getEmail())) {
      validator.validateEmail(socialLoginBo.getEmail());
    }
  }

  public void validateForResetCredentials(ResetCredentialsBo resetPasswordBo) throws RingException {
    validator.validatePassword(resetPasswordBo.getPassword());
    
  }

}
