package com.ring.util;

import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.stereotype.Service;

@Service
public class ClientInfoValidator extends BoValidator {
  
  public void validateClientId(String clientId) throws RingException {
    validateString(clientId,
        new RingException(RingErrorCode.RA_1002, RingResponseCode.OK));
    
  }
  
  public void validateClientSecret(String clientSecret) throws RingException {
    validateString(clientSecret,
        new RingException(RingErrorCode.RA_1003, RingResponseCode.OK));
  }
  
  public void validateUserId(Integer userId) throws RingException {
    validateObject(userId,
        new RingException(RingErrorCode.RA_1005, RingResponseCode.OK));
  }

}
