package com.ring.service;

import com.ring.bo.ResetCredentialsBo;
import com.ring.bo.UserProfileBo;
import com.ring.dao.RecoverAccountDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * <p>
 * Implementation class of ForgotPasswordService interface to provide services
 * to the ForgotPasswordController class. Uses ForgotPasswordDao interface and
 * its implementation class for database services.
 * </p>
 */
@Service("recoverAccountService")
public class RecoverAccountServiceImpl implements RecoverAccountService {
  /**
   * Dao object.
   */
  @Autowired
  private RecoverAccountDao recoverAccountDao;

  @Autowired
  private EmailServiceImpl emailService;

  @Override
  public void sendMail(String email) throws RingException {
    String token = DigestUtils
        .sha1Hex(String.valueOf(System.currentTimeMillis())
            + String.valueOf(UUID.randomUUID()));
    try {
      UserProfileBo userData = recoverAccountDao.getUserInfoFromDb(email);
      emailService.sendUpdatePasswordLink(userData, token);
      recoverAccountDao.persistTokenInDb(userData.getUserId(), token);
    } catch (EmptyResultDataAccessException ex) {
      throw new RingException(RingErrorCode.RA_4001, RingResponseCode.OK);
    }

  }

  @Override
  public void resetCredentials(ResetCredentialsBo resetCredentialsBo)
      throws RingException {
    try {
      ResetCredentialsBo dataFromDb = recoverAccountDao
          .getDataBasedOnToken(resetCredentialsBo.getResetToken());
      checkTokenValidity(dataFromDb.getResetTokenId(),
          dataFromDb.getResetTokenExpTime());
      recoverAccountDao.resetPassword(resetCredentialsBo.getPassword(),dataFromDb);
    } catch (EmptyResultDataAccessException ex) {
      throw new RingException(RingErrorCode.RA_4003, RingResponseCode.OK);
    }

  }

  private void checkTokenValidity(int tokenId, String tokenExpiryTime)
      throws RingException {
    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd hh:mm:ss a z");
    ZonedDateTime timeObj = ZonedDateTime.parse(tokenExpiryTime,formatter);
    ZonedDateTime now = ZonedDateTime.parse(formatter.format(ZonedDateTime.now()),formatter);
    if (now.isAfter(timeObj.plusMinutes(30))) {
      recoverAccountDao.deactivateToken(tokenId);
      throw new RingException(RingErrorCode.RA_4003, RingResponseCode.OK);
    }
  }

}
