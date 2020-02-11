package com.ring.service;

import com.ring.constants.PressureUnitType;
import com.ring.exceptions.RingException;

import java.util.List;

public interface SavingService {
  
  Object getSavingData(double initialPressure, double targetPressure,
      int userId, PressureUnitType pressureUnitType) throws RingException;

}
