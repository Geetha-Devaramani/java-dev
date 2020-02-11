package com.ring.service;

import com.ring.bo.ClientBo;
import com.ring.bo.NextScreenInformationBo;
import com.ring.bo.UserProfileBo;
import com.ring.constants.AppConstant;
import com.ring.constants.RingMessageCode;
import com.ring.dao.UserDao;
import com.ring.dao.VehicleDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.util.ResponseUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/**
 * <p>
 * Implementation class of SignUpService interface to provide services to the
 * SignUpController class. Uses SignUpDao interface and its implementation class
 * for database services.
 * </p>
 */

@Service("registerService")
public class UserServiceImpl implements UserService {

  /**
   * Dao object.
   */
  @Autowired
  UserDao userDao;

  @Autowired
  VehicleDao dao;

  @Autowired
  NextScreenInformationBo next;

  /**
   * logger.
   */
  private static final Logger LOGGER = LogManager
      .getLogger(UserServiceImpl.class);

  /**
   * method to register a user into app.
   */

  @Override
  public int registerUser(UserProfileBo signUpBo)
      throws RingException {

    int userId;
    try {
      userId = userDao.registerUser(signUpBo);
    } catch (RingException exception) {
      LOGGER.error(exception.getMessage());
      LOGGER.debug(exception.getErrorCode().name());
      throw exception;
      // throw throwExceptionBasedOnErrorCode(daoException.getErrorCode().name());
    }

    return userId;
  }

  @Override
  public void registerClient(ClientBo clientBo) {
    userDao.registerClient(clientBo);
  }

  @Override
  public Object getUserInfo(int userId) throws RingException {
    try {
      return userDao.getUserInfo(userId);
    } catch (EmptyResultDataAccessException ex) {
      throw new RingException(RingErrorCode.RA_2007, RingResponseCode.OK);
    }
  }

  public Object getNextScreeninfo(ClientBo clientBo) throws RingException {

    NextScreenInformationBo nextScreenInformation = new NextScreenInformationBo();
    int count = (int) userDao.getUserDetails(clientBo.getUserId());
    boolean status = userDao.getClientDetails(clientBo.getClientId());
    if (clientBo.getUserId() == AppConstant.NEGATIVE) {
      nextScreenInformation = getClientScreenDetails(status);

    } else if (status != false) {
      nextScreenInformation = checkuserExist(clientBo, nextScreenInformation,
          count);
    } else {
      throw new RingException(RingErrorCode.RA_1006, RingResponseCode.OK);
    }
    nextScreenInformation.setHelpUrl(next.getHelpUrl());
    return nextScreenInformation;
  }

  private NextScreenInformationBo checkuserExist(ClientBo clientBo,
      NextScreenInformationBo nextScreenInformation, int count)
      throws RingException {
    if (count == AppConstant.ZERO) {
      throw new RingException(RingErrorCode.RA_1007, RingResponseCode.OK);
    } else {
      nextScreenInformation = getVehicleScreenDetails(clientBo);
    }
    return nextScreenInformation;
  }

  private NextScreenInformationBo getVehicleScreenDetails(ClientBo clientBo)
      throws RingException {

    NextScreenInformationBo nextScreenInformation = new NextScreenInformationBo();
    List<Map<String, Object>> vehicleList = dao
        .getVehicleList(clientBo.getUserId());
    getVehicleDetails(nextScreenInformation, vehicleList);
    return nextScreenInformation;
  }

  private void getVehicleDetails(NextScreenInformationBo nextScreenInformation,
      List<Map<String, Object>> vehicleList) throws RingException {
    if (vehicleList.size() > 0) {
      nextScreenInformation.setCode(AppConstant.THREE);
      nextScreenInformation.setScreenMessage(
          ResponseUtil.genericMessage(RingMessageCode.RM_5002.name()));
    } else {
      nextScreenInformation.setCode(AppConstant.FOUR);
      nextScreenInformation.setScreenMessage(
          ResponseUtil.genericMessage(RingMessageCode.RM_5003.name()));

    }
  }

  private NextScreenInformationBo getClientScreenDetails(boolean status)
      throws RingException {
    NextScreenInformationBo nextScreenInformation = new NextScreenInformationBo();
    if (status) {

      nextScreenInformation.setCode(AppConstant.TWO);
      nextScreenInformation.setScreenMessage(
          ResponseUtil.genericMessage(RingMessageCode.RM_5001.name()));
    } else {
      nextScreenInformation.setCode(AppConstant.ONE);
      nextScreenInformation.setScreenMessage(
          ResponseUtil.genericMessage(RingMessageCode.RM_5000.name()));

    }
    return nextScreenInformation;
  }

  public void updateInfo(int userId, UserProfileBo userProfileBo)
      throws RingException {
    String query = updateFields(userProfileBo);
    int returnValue;

    returnValue = userDao.updateInfo(userId, userProfileBo, query);
    if (returnValue == 0) {
      throw new RingException(RingErrorCode.RA_2007, RingResponseCode.OK);
    }
  }

  private String updateFields(UserProfileBo userProfileBo) {
    StringBuilder query = new StringBuilder(
        "update tbl_user set usr_user_name = ?,usr_lastmodified_datetime = ?");
    if (userProfileBo.getGender() != null) {
      updateStringFields(userProfileBo.getGender().getGenderType(),
          "usr_gender",
          query);
    } else {
      updateStringFields(null, "usr_gender",
          query);
    }
    updateStringFields(userProfileBo.getDob(), "usr_date_of_birth", query);
    updateStringFields(userProfileBo.getEmail(), "usr_email", query);
    updateStringFields(userProfileBo.getPhoneNumber(), "usr_phone_number",
        query);
    return query.append(" where usr_user_id=?").toString();

  }

  private void updateStringFields(String value, String columnName,
      StringBuilder query) {
    if (value != null) {
      query.append("," + columnName + "='" + value.trim() + "'");
    } else {
      query.append("," + columnName + "=" + value);
    }
  }

  @Override
  public List<String> getQuaters() throws RingException {
    List<String> quarters = new ArrayList<>();
    LocalDate dateInfirstMonthInAQuarter = LocalDate.now();
    do {
      quarters.add(getMonthYearString(dateInfirstMonthInAQuarter.minusMonths(2))
          + "-" + getMonthYearString(dateInfirstMonthInAQuarter));
      dateInfirstMonthInAQuarter = dateInfirstMonthInAQuarter.minusMonths(3);
    } while (!LocalDate.now().minusMonths(12).getMonth()
        .equals(dateInfirstMonthInAQuarter.getMonth()));
    return quarters;
  }

  private static String getMonthYearString(LocalDate date) {
    return date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        .toUpperCase() + " " + date.getYear();
  }
}
