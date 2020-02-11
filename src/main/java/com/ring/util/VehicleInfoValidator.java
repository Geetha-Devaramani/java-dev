package com.ring.util;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleAutoDataBo;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

@Service
public class VehicleInfoValidator extends BoValidator {

  @Autowired
  AutoDataInfoValidator autoDataInfoValidator;
  
  @Autowired
  PressureInfoValidator pressureInfoValidator;

  public void validateVehicleName(String vehicleName)
      throws RingException {
    final Pattern vehicleNameRegex = Pattern.compile("^.{2,50}$",
        Pattern.CASE_INSENSITIVE);
    if (!vehicleNameRegex.matcher(vehicleName).matches()) {
      throw new RingException(RingErrorCode.RA_3005,
          RingResponseCode.OK);
    }

  }

  public void validateRegistrationNumber(String registrationNumber)
      throws RingException {
    final Pattern registrationNumberRegex = Pattern.compile("^[A-Z0-9 ]{7,8}$",
        Pattern.CASE_INSENSITIVE);
    if (!registrationNumberRegex.matcher(registrationNumber).matches()) {
      throw new RingException(RingErrorCode.RA_3002,
          RingResponseCode.OK);
    }

  }

  public void validateVin(String vin) throws RingException {
    final Pattern vinRegex = Pattern.compile("^[A-Z0-9]{17,17}$",
        Pattern.CASE_INSENSITIVE);
    if (!vinRegex.matcher(vin).matches()) {
      throw new RingException(RingErrorCode.RA_3015,
          RingResponseCode.OK);
    }

  }

  public void validateCarMake(String carMake) throws RingException {
    validateString(carMake,
        new RingException(RingErrorCode.RA_3003, RingResponseCode.OK));

  }

  public void validateCarModel(String carModel) throws RingException {
    validateString(carModel,
        new RingException(RingErrorCode.RA_3004, RingResponseCode.OK));
  }
  
  public void validateBodyStyle(String bodyStyle) throws RingException {
    validateString(bodyStyle,
        new RingException(RingErrorCode.RA_3021, RingResponseCode.OK));
  }

  public void validateYearOfManufacture(String year) throws RingException {
    validateString(year,
        new RingException(RingErrorCode.RA_3013, RingResponseCode.OK));
    final Pattern vinRegex = Pattern.compile("^[0-9-]{4,9}$",
        Pattern.CASE_INSENSITIVE);
    if (!vinRegex.matcher(year).matches()) {
      throw new RingException(RingErrorCode.RA_3019,
          RingResponseCode.OK);
    }
  }

  public void validateLoad(PressureBo pressureBo) throws RingException {
    pressureInfoValidator.validateTyreSize(pressureBo.getTyreSize());
    pressureInfoValidator
    .validatePressureUnitType(pressureBo.getPressureUnitType());
    pressureInfoValidator.validateLoadType(pressureBo.getLoadType());
    pressureInfoValidator
        .validateFrontTyrePressure(pressureBo.getFrontTyrePressure());
    pressureInfoValidator
        .validateRearTyrePressure(pressureBo.getRearTyrePressure());
    
  
  }
  
  public void validateAutoData(VehicleAutoDataBo vehicleAutoDataBo) throws RingException {
    autoDataInfoValidator.validateMnufacturerId(vehicleAutoDataBo.getManufacturerId());
    autoDataInfoValidator.validateModelId(vehicleAutoDataBo.getModelId());
    autoDataInfoValidator.validateMId(vehicleAutoDataBo.getmId());
    autoDataInfoValidator.validateTyrePressureId(vehicleAutoDataBo.getTyrePressureId());
  }

}
