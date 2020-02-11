package com.ring.service;

import com.mongodb.DBObject;
import com.ring.dao.TyrePressureDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class implementing TyrePressureService.
 * 
 * @author ee209986
 *
 */

@Service("pressureService")
public class TyrePressureServiceImpl implements TyrePressureService {
  /**
   * Dao object.
   */
  @Autowired
  TyrePressureDao dao;

  /**
   * method to get lastInflated days of each tyre .
   */
  @Override
  public Map<String, Object> getLatestPressureData(int userId, int vehicleId)
      throws RingException {
    final Map<String, Object> dataResponseMap = new HashMap<>();
    dataResponseMap.put("FR", -1);
    dataResponseMap.put("FL", -1);
    dataResponseMap.put("RR", -1);
    dataResponseMap.put("RL", -1);
    List<DBObject> dataFromDb = dao.getLatestPressureData(userId, vehicleId);
    dataResponseMap.keySet()
        .forEach(key -> dataFromDb.stream().filter(dbObject -> String
            .valueOf(dbObject.get("_id")).equalsIgnoreCase(key)).findAny()
            .ifPresent(dbObject -> dataResponseMap.put(key,
                computeLastInflatedInDays(
                    String.valueOf(dbObject.get("lastUpdatedTime"))))));

    return dataResponseMap;
  }

  private static long computeLastInflatedInDays(String lastUpdated) {
    return Instant.ofEpochSecond(Long.parseLong(lastUpdated))
        .atZone(ZoneId.systemDefault()).toLocalDate()
        .until(LocalDate.now(), ChronoUnit.DAYS);
  }

  @Override
  public List<DBObject> getTyrePressureData(int userId, int vehicleId,
      String fromMonthYear,
      String toMonthYear, String wheelType) throws RingException {
    List<DBObject> dataFromDb = dao.getTyrePressureData(userId, vehicleId,
        getLocalDateFromMonthYearString(fromMonthYear)
            .toEpochDay(),
        getLastDateOfMonth(getLocalDateFromMonthYearString(toMonthYear))
            .toEpochDay(),
        wheelType);

    if (dataFromDb.isEmpty()) {
      throw new RingException(RingErrorCode.RA_3010, RingResponseCode.OK);
    } else {
      dataFromDb.forEach(dbObject -> dbObject.put("date",
          getDateStringFromEpochDay(Double
              .valueOf(String.valueOf(dbObject.get("date"))).longValue())));
    }
    return dataFromDb;
  }

  private LocalDate getLocalDateFromMonthYearString(String monthYear) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseLenient().parseCaseInsensitive()
        .append(DateTimeFormatter.ofPattern("ddMMMyyyy"))
        .toFormatter().withZone(ZoneId.systemDefault());
    return LocalDate.parse("1" + monthYear.trim().replace(" ", ""),
        formatter);
  }

  private String getDateStringFromEpochDay(long epochDay) {
    LocalDate localdate = LocalDate
        .ofEpochDay(epochDay);
    return localdate.getDayOfMonth() + " " + Month.of(localdate.getMonthValue())
        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase() + " "
        + localdate.getYear();
  }

  private LocalDate getLastDateOfMonth(LocalDate date) {
    return date.withDayOfMonth(
        date.getMonth().length(date.isLeapYear()));
  }
}
