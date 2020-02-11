package com.ring.dao;

import com.mongodb.DBObject;
import com.ring.exceptions.RingException;

import java.util.List;
/**
 * interface which declares methods for database services required for graph.
 * 
 * @author ee209986
 *
 */

public interface TyrePressureDao {

  List<DBObject> getLatestPressureData(int userId, int vehicleId)
      throws RingException;

  List<DBObject> getTyrePressureData(int userId, int vehicleId,
      long fromEpochDay,
      long toEpochDay, String wheelType);

}
