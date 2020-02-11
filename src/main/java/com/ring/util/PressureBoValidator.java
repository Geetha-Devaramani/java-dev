package com.ring.util;

import com.ring.bo.PressureBo;
import com.ring.exceptions.RingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PressureBoValidator {
  
  @Autowired
  private PressureInfoValidator validator;
  
  public void validateForPressureSettingsUpdate(PressureBo pressureBo) throws RingException {
    validator.validateFrontTyrePressure(pressureBo.getFrontTyrePressure());
    validator.validateRearTyrePressure(pressureBo.getRearTyrePressure());
    validator.validateTyreSize(pressureBo.getTyreSize());
    validator.validateLoadType(pressureBo.getLoadType());
    validator.validatePressureUnitType(pressureBo.getPressureUnitType());
    
  }

}
