package com.ring.dao;

import com.ring.bo.ClientBo;
import com.ring.bo.SocialLoginBo;
import com.ring.bo.UserProfileBo;
import com.ring.exceptions.RingException;

/**
 * interface which declares methods for database services required for signup.
 * 
 * @author ee209986
 *
 */

public interface UserDao {

	int registerUser(UserProfileBo signUpBo) throws RingException;
	void registerSocialUser(SocialLoginBo socialUserBo);
	void registerClient(ClientBo clientBo);
	Object getUserInfo(int userId);
	boolean getClientDetails(String clientId);
	int getUserDetails(int id);
  int updateInfo(int userId, UserProfileBo userDetailsBo, String query);

}
