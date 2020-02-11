package com.ring.dao;

import com.ring.bo.ClientBo;
import com.ring.bo.SocialLoginBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.AppConstant;
import com.ring.constants.GenderType;
import com.ring.constants.LoginType;
import com.ring.constants.Status;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.util.DaoUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * <p>
 * Implementation class of SignUpDao interface. to provide services to the
 * SignUpServiceImpl class.
 * </p>
 */

@Repository
public class UserDaoImpl implements UserDao {

  /**
   * Jdbc template.
   */
  @Autowired
  JdbcTemplate jdbctemplate;

  @Autowired
  PasswordEncoder passwordEncoder;

  private static final String USER_REGISTER_QUERY = "INSERT INTO tbl_user(usr_login_id, usr_login_type, usr_user_name, "
      + "usr_email,usr_rol_role_id, usr_password, usr_phone_number, usr_date_of_birth,usr_gender,"
      + "usr_status, usr_created_user_id, usr_created_datetime,"
      + "usr_lastmodified_user_id, usr_lastmodified_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SOCIAL_USER_REGISTER_QUERY = "INSERT INTO "
      + "tbl_user(usr_email,usr_login_id,usr_password,usr_user_name,"
      + "usr_login_type,usr_status,usr_created_datetime,usr_lastmodified_datetime,"
      + "usr_created_user_id,usr_lastmodified_user_id,usr_rol_role_id) "
      + "VALUES(?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT (usr_login_id) DO NOTHING";

  private static final String SOCIAL_USER_NO_EMAIL_REGISTER_QUERY = "INSERT INTO "
      + "tbl_user(usr_login_id,usr_password,usr_user_name,usr_login_type,"
      + "usr_status,usr_created_datetime,usr_lastmodified_datetime,"
      + "usr_created_user_id,usr_lastmodified_user_id,usr_rol_role_id) "
      + "VALUES(?,?,?,?,?,?,?,?,?,?) ON CONFLICT(usr_login_id) DO NOTHING";

  private static final String REGISTER_CLIENT_QUERY = "INSERT into oauth_client_details"
      + "(client_id,client_secret) VALUES(?,?)"
      + "ON CONFLICT (client_id) DO NOTHING";

  private static final String GET_CLIENT_INFO = "SELECT COUNT(*) FROM oauth_client_details"
      + " WHERE client_id = ? ";

  private static final String GET_USER_INFO_QUERY = "SELECT usr_user_name \"userName\","
      + " usr_gender \"gender\", usr_date_of_birth \"dob\", usr_email \"email\","
      + "usr_phone_number \"phoneNumber\",usr_login_type \"loginType\" "
      + "FROM tbl_user WHERE usr_user_id = ? AND usr_status = 'Active'";

  private static final String GET_USER_INFO = "SELECT COUNT(*) FROM tbl_user"
      + " WHERE usr_user_id = ? ";

  /**
   * logger.
   */
  private static final Logger LOGGER = LogManager
      .getLogger(UserDaoImpl.class);

  /**
   * method to register a user if optional vin field is not given by user .
   * 
   * @throws RingException
   */
  @Override
  public int registerUser(UserProfileBo signUpBo) throws RingException {
    String password = passwordEncoder.encode(signUpBo.getPassword());

    KeyHolder holder = new GeneratedKeyHolder();
    try {
      jdbctemplate.update(insertUser(signUpBo, password), holder);
      return (int) holder.getKeys().get("usr_user_id");
    } catch (DuplicateKeyException duplicateKeyException) {
      LOGGER.error(duplicateKeyException.getMessage());
      throw new RingException(RingErrorCode.RA_2004,
          RingResponseCode.OK);
    }
  }

  private PreparedStatementCreator insertUser(UserProfileBo signUpBo,
      String password) {
    return new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection)
          throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            USER_REGISTER_QUERY,
            Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, signUpBo.getLoginId().trim());
        ps.setString(2, LoginType.APPLICATION.getloginType());
        ps.setString(3, signUpBo.getUserName());
        ps.setString(4, signUpBo.getLoginId());
        ps.setInt(5, 1);
        ps.setString(6, password);
        ps.setString(7, signUpBo.getPhoneNumber());
        ps.setString(8, signUpBo.getDob());
        ps.setObject(9, signUpBo.getGender());
        ps.setString(10, Status.ACTIVE.getStatus());
        ps.setInt(11, AppConstant.ONE);
        ps.setString(12, DaoUtil.getDate());
        ps.setInt(13, AppConstant.ONE);
        ps.setString(14, DaoUtil.getDate());
        return ps;
      }
    };
  }

  @Override
  public void registerSocialUser(SocialLoginBo socialUserBo) {
    String password = passwordEncoder
        .encode("RingAuto_" + socialUserBo.getLoginId());
    if (socialUserBo.getEmail() == null) {
      jdbctemplate.update(SOCIAL_USER_NO_EMAIL_REGISTER_QUERY,
          socialUserBo.getLoginId(),
          password, socialUserBo.getUserName(),
          socialUserBo.getLoginType().getloginType(), Status.ACTIVE.getStatus(),
          DaoUtil.getDate(), DaoUtil.getDate(), AppConstant.ONE,
          AppConstant.ONE, AppConstant.ONE);
    } else {
      jdbctemplate.update(SOCIAL_USER_REGISTER_QUERY,
          socialUserBo.getEmail(),
          socialUserBo.getLoginId(), password, socialUserBo.getUserName(),
          socialUserBo.getLoginType().getloginType(), Status.ACTIVE.getStatus(),
          DaoUtil.getDate(), DaoUtil.getDate(), AppConstant.ONE,
          AppConstant.ONE, AppConstant.ONE);
    }
  }

  @Override
  public void registerClient(ClientBo clientBo) {
    jdbctemplate.update(REGISTER_CLIENT_QUERY, clientBo.getClientId(),
        clientBo.getClientSecret());
  }

  @Override
  public Object getUserInfo(int userId) {
    return jdbctemplate.queryForMap(GET_USER_INFO_QUERY, userId);
  }

  public boolean getClientDetails(String clientId) {
    int i = jdbctemplate.queryForObject(GET_CLIENT_INFO,
        new Object[]{clientId}, Integer.class);
    if (i > AppConstant.ZERO) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int getUserDetails(int userId) {
    int i = jdbctemplate.queryForObject(GET_USER_INFO,
        new Object[]{userId}, Integer.class);
    return i;

  }

  @Override
  public int updateInfo(int userId, UserProfileBo userDetailsBo,
      String query) {
    return jdbctemplate.update(query, userDetailsBo.getUserName().trim(),
        DaoUtil.getDate(),
        userId);
  }
}
