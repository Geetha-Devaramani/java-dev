package com.ring.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ring.bo.UserProfileBo;
import com.ring.exceptions.RingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

  @Autowired
  JdbcTemplate jdbcTemplate;

  private ObjectMapper mapper = new ObjectMapper();

  private static final String FETCH_USER_LOGIN_INFO = "SELECT usr_user_id \"userId\""
      + ", usr_login_id \"loginId\", usr_login_type \"loginType\", usr_user_name "
      + "\"userName\" ,usr_password \"password\" FROM tbl_user WHERE "
      + "usr_login_id = ? AND usr_status = 'Active'";

  /**
   * .
   * 
   * @throws RingException
   */
  @Override
  public UserProfileBo getUser(String loginId) {
    return mapper.convertValue(
        jdbcTemplate.queryForMap(FETCH_USER_LOGIN_INFO, loginId),
        UserProfileBo.class);
  }
}
