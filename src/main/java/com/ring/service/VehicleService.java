package com.ring.service;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleBo;
import com.ring.exceptions.RingException;

import java.util.List;
import java.util.Map;

public interface VehicleService {

  Map<String, Object> addVehicle(int userId, VehicleBo vehicleDetails)
      throws RingException;

  void editVehicle(int userId, int vehicleId, VehicleBo vehicleDetails)
      throws RingException;

  void editPressureSettings(int userId, int vehicleId,
      PressureBo vehiclePressureDetails);

  List<Map<String, Object>> getVehicleList(int userId) throws RingException;

  Map<String, Object> getVehicle(int vehicleId) throws RingException;

  void deleteVehicle(int vehicleId, int userId) throws RingException;

  void updateDefaultstatus(int vehicleId, int vehicleId2);
}