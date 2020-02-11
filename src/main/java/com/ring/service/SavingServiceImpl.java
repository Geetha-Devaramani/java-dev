package com.ring.service;

import com.ring.bo.SavingBo;
import com.ring.constants.PressureUnitType;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.util.SavingUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SavingServiceImpl implements SavingService{

  @Autowired
  SavingBo savingData;

  @Autowired
  SavingUtil saving;

  @Override
  public Map<String, Object> getSavingData(double initialPressure, double targetPressure,
      int userId, PressureUnitType pressureUnitType) throws RingException {
    Map<String, Object> map = new HashMap<>();
    double changeInPressureData = (targetPressure - initialPressure);
    if(changeInPressureData > 6){
      changeInPressureData = 6;
    }
    if(changeInPressureData >= 0) {
      double convertedSavingDataInPsi = saving.convertPressure(pressureUnitType.getPresureType(), changeInPressureData);
      String savedMoney = String.valueOf(SavingUtil.round(convertedSavingDataInPsi*savingData.getMoneySaving(),2));
      String savedCarbonData = String.valueOf(SavingUtil.round(convertedSavingDataInPsi*savingData.getCarbonSaving(),2));
      map.put("moneySaved",savedMoney);
      map.put("carbonSaving",savedCarbonData);
    } else {
      throw new RingException(RingErrorCode.RA_7000, RingResponseCode.OK);
    }
    return map;
  }



}
