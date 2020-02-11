package com.ring.controller;

import com.ring.bo.PressureBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.VehicleService;
import com.ring.util.PressureBoValidator;
import com.ring.util.ResponseUtil;
import com.ring.util.VehicleBoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(UrlConstantsBo.USERS + UrlConstantsBo.USER_ID
    + UrlConstantsBo.VEHICLES)
public class VehicleController {

  @Autowired
  VehicleService service;
  
  @Autowired
  VehicleBoValidator vehicleBoValidator;
  
  @Autowired
  PressureBoValidator pressureBoValidator;

  ResponseDataBo responseDataBo = new ResponseDataBo();

  ResponseBo responseBo = new ResponseBo();

  @RequestMapping(
      method = RequestMethod.POST)
  public ResponseEntity<ResponseDataBo> addVehicle(@PathVariable("userId") int userId,
      final @RequestBody VehicleBo vehicleDetails)
          throws Exception {
    vehicleBoValidator.validateForVehicleAddition(vehicleDetails);
    return new ResponseEntity<>( ResponseUtil.genericResponseData
        (RingMessageCode.RM_3000.name(),service.addVehicle(userId, vehicleDetails)), HttpStatus.OK);

  }

  @RequestMapping(
      value = UrlConstantsBo.VEHICLE_ID,
      method = RequestMethod.PUT)
  public ResponseEntity<ResponseBo> editVehicle(
      @PathVariable("userId") int userId,
      @PathVariable("vehicleId") int vehicleId,
      final @RequestBody VehicleBo vehicleDetails)
          throws Exception {
    vehicleBoValidator.validateForVehicleUpdate(vehicleDetails);
    service.editVehicle(userId, vehicleId, vehicleDetails);
    return new ResponseEntity<>(ResponseUtil.genericResponse
        (RingMessageCode.RM_3001.name()), HttpStatus.OK);

  }



  @RequestMapping(
      value = UrlConstantsBo.VEHICLE_ID
      + UrlConstantsBo.PRESSURE_SETTINGS,
      method = RequestMethod.PUT)
  public ResponseEntity<ResponseBo> editPressureSettings(
      @PathVariable("userId") int userId,
      @PathVariable("vehicleId") int vehicleId,
      final @RequestBody PressureBo vehiclePressureDetails)
          throws RingException {
    pressureBoValidator.validateForPressureSettingsUpdate(vehiclePressureDetails);
    service.editPressureSettings(userId, vehicleId, vehiclePressureDetails);
    return new ResponseEntity<>(ResponseUtil.genericResponse
        (RingMessageCode.RM_3002.name()), HttpStatus.OK);
  }


  @RequestMapping(
      value = UrlConstantsBo.VEHICLE_ID+ UrlConstantsBo.DEFAULT_VEHICLE,
      method = RequestMethod.PUT)
  public ResponseEntity<ResponseBo> updateDefaultstatus(
      @PathVariable("userId") int userId,
      @PathVariable("vehicleId") int vehicleId)
          throws RingException {
    service.updateDefaultstatus(userId,vehicleId);
    return new ResponseEntity<>(ResponseUtil.genericResponse
        (RingMessageCode.RM_3003.name()), HttpStatus.OK);
  }

  @RequestMapping(
      method = RequestMethod.GET)
  public ResponseEntity<ResponseDataBo> getVehicleList(
      @PathVariable("userId") int userId)
          throws RingException {
    return new ResponseEntity<>( ResponseUtil.genericResponseData
        (RingMessageCode.RM_3004.name(),service.getVehicleList(userId)), HttpStatus.OK);

  }

  @RequestMapping(
      value = UrlConstantsBo.VEHICLE_ID,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseDataBo> getVehicle(@PathVariable("vehicleId") int vehicleId)
      throws RingException {
    return new ResponseEntity<>( ResponseUtil.genericResponseData
        (RingMessageCode.RM_3005.name(),service.getVehicle(vehicleId)), HttpStatus.OK);

  }


  @RequestMapping(
      value = UrlConstantsBo.VEHICLE_ID,
      method = RequestMethod.DELETE)
  public ResponseEntity<ResponseBo> deleteVehicle(@PathVariable("vehicleId") 
  int vehicleId,@PathVariable("userId") int userId)
      throws RingException {
    service.deleteVehicle(vehicleId,userId);
    return new ResponseEntity<>(ResponseUtil.genericResponse
        (RingMessageCode.RM_3006.name()), HttpStatus.OK);

  }

}
