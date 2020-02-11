package com.ring.service;

import com.mongodb.DBObject;
import com.ring.exceptions.RingException;

import java.util.List;
import java.util.Map;

/**
 * inteface used by TyrePressureController for services.
 * 
 * @author ee209986
 *
 */
public interface TyrePressureService {

  Map<String, Object> getLatestPressureData(int userId, int vehicleId)
      throws RingException;

  List<DBObject> getTyrePressureData(int userId, int vehicleId,
      String fromMonthYear,
      String toMonthYear, String tyreType) throws RingException;

}
