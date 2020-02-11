package com.ring.util;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.AppConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Service
public class VehicleBoValidator {

  @Autowired
  VehicleInfoValidator validator;

  private Class<? extends VehicleInfoValidator> validatorClass = VehicleInfoValidator.class;

  public void validateForVehicleAddition(VehicleBo vehicleBo)
      throws Exception {

    validate(validatorClass.getMethod("validateVehicleName", String.class),
        vehicleBo.getVehicleName());

    validate(
        validatorClass.getMethod("validateRegistrationNumber", String.class),
        vehicleBo.getRegistrationNumber());

    validate(validatorClass.getMethod("validateVin", String.class),
        vehicleBo.getVin());

    validator.validateCarMake(vehicleBo.getCarMake());

    validator.validateCarModel(vehicleBo.getCarModel());

    validate(
        validatorClass.getMethod("validateYearOfManufacture", String.class),
        vehicleBo.getYearOfManufacture());

    validate(validatorClass.getMethod("validateBodyStyle", String.class),
        vehicleBo.getBodyStyle());

    List<PressureBo> load = vehicleBo.getLoad();
    for (int i = AppConstant.ZERO; i < load.size(); i++) {
      validator.validateLoad(vehicleBo.getLoad().get(i));
    }
  }

  public void validateForVehicleUpdate(VehicleBo vehicleBo)
      throws Exception {

    validate(validatorClass.getMethod("validateVehicleName", String.class),
        vehicleBo.getVehicleName());

    validate(
        validatorClass.getMethod("validateRegistrationNumber", String.class),
        vehicleBo.getRegistrationNumber());

    validate(validatorClass.getMethod("validateVin", String.class),
        vehicleBo.getVin());

    validate(
        validatorClass.getMethod("validateYearOfManufacture", String.class),
        vehicleBo.getYearOfManufacture());

    validate(validatorClass.getMethod("validateCarMake", String.class),
        vehicleBo.getCarMake());

    validate(validatorClass.getMethod("validateCarModel", String.class),
        vehicleBo.getCarModel());

    validate(validatorClass.getMethod("validateBodyStyle", String.class),
        vehicleBo.getBodyStyle());

    List<PressureBo> load = vehicleBo.getLoad();
    if (!Objects.isNull(load) && !load.isEmpty()) {
      for (int i = AppConstant.ZERO; i < load.size(); i++) {
        validator.validateLoad(load.get(i));
      }
    }
  }

  private void validate(Method method,
      Object obj) throws Exception {
    if (!Objects.isNull(obj)) {
      try {
        method.invoke(validator, obj);
      } catch (InvocationTargetException exp) {
        throw (Exception) exp.getTargetException();
      }
    }
  }

}
