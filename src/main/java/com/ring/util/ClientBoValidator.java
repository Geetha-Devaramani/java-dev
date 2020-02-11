package com.ring.util;

import com.ring.bo.ClientBo;
import com.ring.exceptions.RingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientBoValidator {
  
  @Autowired
  ClientInfoValidator validator;
  
  public void validateForClientAddition(ClientBo clientBo) throws RingException {
    validator.validateClientId(clientBo.getClientId());
    validator.validateClientSecret(clientBo.getClientSecret());
  }
  
  public void validateForNextScreenInfo(ClientBo clientBo) throws RingException {
    validateForClientAddition(clientBo);
    validator.validateUserId(clientBo.getUserId());
  }

}
