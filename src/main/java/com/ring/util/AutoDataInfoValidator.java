package com.ring.util;

import org.springframework.stereotype.Service;

import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

@Service
public class AutoDataInfoValidator extends BoValidator {
  
  public void validateMnufacturerId(String manufacturerId)
      throws RingException {
    validateObject(manufacturerId,
        new RingException(RingErrorCode.RA_6000, RingResponseCode.OK));
  }


  public void validateModelId(String modelId)
      throws RingException {
    validateObject(modelId,
        new RingException(RingErrorCode.RA_6001, RingResponseCode.OK));
  }

  public void validateMId(String mId)
      throws RingException {
    validateObject(mId,
        new RingException(RingErrorCode.RA_6002, RingResponseCode.OK));
  }

  public void validateTyrePressureId(String tyrePressureId)
      throws RingException {
    validateObject(tyrePressureId,
        new RingException(RingErrorCode.RA_6003, RingResponseCode.OK));
  }

}
