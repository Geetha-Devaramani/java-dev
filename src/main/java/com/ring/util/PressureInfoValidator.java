package com.ring.util;

import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.stereotype.Service;

@Service
public class PressureInfoValidator extends BoValidator {

  public void validateFrontTyrePressure(Double frontTyrePressure)
      throws RingException {
    validateObject(frontTyrePressure,
        new RingException(RingErrorCode.RA_5000, RingResponseCode.OK));
  }

  public void validateRearTyrePressure(Double rearTyrePressure)
      throws RingException {
    validateObject(rearTyrePressure,
        new RingException(RingErrorCode.RA_5001, RingResponseCode.OK));
  }

  public void validateTyreSize(String tyreSize) throws RingException {
    validateString(tyreSize,
        new RingException(RingErrorCode.RA_5002, RingResponseCode.OK));
  }

  public void validateLoadType(LoadType loadType)
      throws RingException {
    validateObject(loadType,
        new RingException(RingErrorCode.RA_5004, RingResponseCode.OK));
  }

  public void validatePressureUnitType(PressureUnitType pressureUnitType)
      throws RingException {
    validateObject(pressureUnitType,
        new RingException(RingErrorCode.RA_5003, RingResponseCode.OK));
  }

}
