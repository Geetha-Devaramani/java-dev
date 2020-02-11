package com.ring.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.Status;
import com.ring.constants.TokenType;
import com.ring.util.DaoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Implementation class of ForgotPasswordDao interface. to provide services to
 * the ForgotPasswordServiceImpl class.
 * </p>
 */
@Repository("recoverAccountDao")
public class RecoverAccountDaoImpl implements RecoverAccountDao {

  /**
   * Jdbc template.
   */
  @Autowired
  private JdbcTemplate template;

  @Autowired
  PasswordEncoder passwordEncoder;

  private ObjectMapper mapper = new ObjectMapper();

  private static final String PERSIST_TOKEN_QUERY = "INSERT INTO public.tbl_token"
      + "(tok_usr_userid, tok_token_data, tok_token_type, "
      + "tok_status, tok_created_user_id, tok_created_datetime, tok_lastmodified_user_id, "
      + "tok_lastmodified_datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
      + "ON CONFLICT (tok_usr_userid) DO UPDATE SET tok_token_data=?, "
      + "tok_status=?, tok_created_user_id = ?, tok_created_datetime = ?, "
      + "tok_lastmodified_user_id=?, tok_lastmodified_datetime=?";

  private static final String GET_USER_INFO_BASED_ON_EMAIL_QUERY = "SELECT "
      + "usr_user_name \"userName\" , usr_user_id \"userId\", usr_login_id \"loginId\" "
      + "FROM tbl_user WHERE usr_login_id = ? AND usr_status = 'Active' "
      + "AND usr_login_type = 'Application'";

  private static final String GET_TOKEN_INFO_QUERY = "SELECT tok_usr_userid "
      + "\"userId\", tok_token_data \"resetToken\", tok_tokenid \"resetTokenId\","
      + " tok_created_datetime \"resetTokenExpTime\" FROM public.tbl_token "
      + "WHERE tok_token_data = ? AND tok_status='Active'";

  private static final String RESET_CREDENTIAL_QUERY = "UPDATE public.tbl_user "
      + "SET usr_password=?, usr_lastmodified_user_id=?, "
      + "usr_lastmodified_datetime=? WHERE usr_user_id=?";

  private static final String DEACTIVATE_TOKEN_QUERY = "UPDATE public.tbl_token "
      + "SET tok_status=?, tok_lastmodified_datetime=? WHERE tok_tokenid=?";

  /**
   * Method to persist token related data in database.
   */
  @Override
  public void persistTokenInDb(int userId, String token) {
    template.update(PERSIST_TOKEN_QUERY, userId, token,
        TokenType.EMAIL.getTokenType(), Status.ACTIVE.getStatus(), userId,
        DaoUtil.getDate(), userId, DaoUtil.getDate(), token,
        Status.ACTIVE.getStatus(), userId, DaoUtil.getDate(), userId,
        DaoUtil.getDate());

  }

  @Override
  public UserProfileBo getUserInfoFromDb(String email) {
    return mapper.convertValue(
        template.queryForMap(GET_USER_INFO_BASED_ON_EMAIL_QUERY, email),
        UserProfileBo.class);
  }

  @Override
  public ResetCredentialsBo getDataBasedOnToken(String token) {
    return mapper.convertValue(
        template.queryForMap(GET_TOKEN_INFO_QUERY, token),
        ResetCredentialsBo.class);
  }

  @Override
  @Transactional
  public void resetPassword(String password, ResetCredentialsBo dataFromDb) {
    template.update(RESET_CREDENTIAL_QUERY,
        passwordEncoder.encode(password),
        dataFromDb.getUserId(), DaoUtil.getDate(),
        dataFromDb.getUserId());
    deactivateToken(dataFromDb.getResetTokenId());

  }

  @Override
  public void deactivateToken(int tokenId) {
    template.update(DEACTIVATE_TOKEN_QUERY, Status.INACTIVE.getStatus(),
        DaoUtil.getDate(),
        tokenId);

  }

}
