package com.ring.dao;

import com.ring.bo.UserProfileBo;

public interface LoginDao {

  UserProfileBo getUser(String loginId);

}
