package com.ring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleBo;
import com.ring.dao.VehicleDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

@Service
public class VehicleServiceImpl implements VehicleService {

  @Autowired
  VehicleDao dao;

  @Override
  public Map<String, Object> addVehicle(int userId,
      VehicleBo vehicleDetails) throws RingException {
    Map<String, Object> map;

    try {
      map = dao.addVehicle(userId, vehicleDetails);

    } catch (RingException exception) {
      throw exception;
    }
    return map;
  }


  @Override
  public void editPressureSettings(int userId, int vehicleId,
      PressureBo vehiclePressureDetails) {
    dao.editPressureSettings(userId, vehicleId, vehiclePressureDetails);

  }

  @Override
  public List<Map<String, Object>> getVehicleList(int userId)
      throws RingException {
    List<Map<String, Object>> vehicleList = dao.getVehicleList(userId);
    return vehicleList;
  }

  @Override
  public Map<String, Object> getVehicle(int vehicleId)
      throws RingException {

     Map<String, Object> vehicle = dao.getVehicle(vehicleId);
    if (vehicle.isEmpty()) {
      throw new RingException(RingErrorCode.RA_3000, RingResponseCode.OK);
    }
    return vehicle;
  }

  @Override
  public void updateDefaultstatus(int userId, int vehicleId) {
    dao.updateDefaultstatus(userId, vehicleId);
  }

  @Override
  public void deleteVehicle(int vehicleId, int userId) throws RingException {
    Map<String, Object> vehicle = dao.getVehicle(vehicleId);
    if (vehicle.isEmpty()) {
      throw new RingException(RingErrorCode.RA_3000, RingResponseCode.OK);
    }
    dao.deleteVehicle(vehicleId, userId);

  }

  @Override
  public void editVehicle(int userId, int vehicleId, VehicleBo vehicleDetails)
      throws RingException {
    dao.editVehicle(userId, vehicleId, vehicleDetails);

  }
}
