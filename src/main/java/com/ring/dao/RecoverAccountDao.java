package com.ring.dao;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.UserProfileBo;

public interface RecoverAccountDao {

  UserProfileBo getUserInfoFromDb(String email);

  void persistTokenInDb(int parseInt, String token);

  ResetCredentialsBo getDataBasedOnToken(String token);

  void resetPassword(String string, ResetCredentialsBo dataFromDb);

  void deactivateToken(int token);

}
