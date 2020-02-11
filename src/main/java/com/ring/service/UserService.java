package com.ring.service;

import com.ring.bo.ClientBo;
import com.ring.bo.UserProfileBo;
import com.ring.exceptions.RingException;

import java.util.List;
import java.util.Map;

/**
 * inteface used by signupController for services.
 * 
 * @author ee209986
 *
 */

public interface UserService {

  int registerUser(UserProfileBo signUpBo) throws RingException;

  void registerClient(ClientBo clientBo) throws RingException;

  Object getUserInfo(int userId) throws RingException;

  Object getNextScreeninfo(ClientBo clientBo) throws RingException;
  
  List<String> getQuaters() throws RingException;
  
  void updateInfo(int userId, UserProfileBo userDetailsBo) throws RingException;
}
