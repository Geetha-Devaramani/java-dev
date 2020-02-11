package com.ring.service;

import com.ring.bo.ResetCredentialsBo;
import com.ring.exceptions.RingException;

public interface RecoverAccountService {

  void sendMail(String email) throws RingException;

  void resetCredentials(ResetCredentialsBo resetPasswordBo)
      throws RingException;

}
