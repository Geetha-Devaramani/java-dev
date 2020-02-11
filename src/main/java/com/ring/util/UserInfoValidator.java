package com.ring.util;

import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserInfoValidator extends BoValidator {

  public void validateEmail(String email) throws RingException {
    validateString(email,
        new RingException(RingErrorCode.RA_2000, RingResponseCode.OK));
    final Pattern emailRegex = Pattern.compile(
        "^(([\\w\\.\\-]{3,})+)@([\\w\\-]+)((\\.(\\w){2,3})+)$",
        Pattern.CASE_INSENSITIVE);;
    if (!emailRegex.matcher(email).matches()) {
      throw new RingException(RingErrorCode.RA_2002,
          RingResponseCode.OK);
    }

  }

  public void validatePassword(String password) throws RingException {
    validateString(password,
        new RingException(RingErrorCode.RA_2001, RingResponseCode.OK));
    final Pattern passwordRegex = Pattern.compile("^.{8,25}$",
        Pattern.UNICODE_CHARACTER_CLASS);
    if (!passwordRegex.matcher(password).matches()) {
      throw new RingException(RingErrorCode.RA_2003,
          RingResponseCode.OK);
    }
  }

  public void validateUserName(String userName) throws RingException {
    validateString(userName,
        new RingException(RingErrorCode.RA_2006, RingResponseCode.OK));
    final Pattern userNameRegex = Pattern.compile("^[A-Z0-9-@._ ]{2,50}$",
        Pattern.CASE_INSENSITIVE);
    if (!userNameRegex.matcher(userName).matches()) {
      throw new RingException(RingErrorCode.RA_2008,
          RingResponseCode.OK);
    }
  }

  public void validatePhoneNumber(String phoneNumber) throws RingException {
    final Pattern phoneNumberRegex = Pattern.compile("^[0-9, +]{10,15}$",
        Pattern.CASE_INSENSITIVE);
    if (!phoneNumberRegex.matcher(phoneNumber).matches()) {
      throw new RingException(RingErrorCode.RA_2009,
          RingResponseCode.OK);
    }
  }

}
