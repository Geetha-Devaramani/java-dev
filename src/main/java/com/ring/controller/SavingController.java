package com.ring.controller;

import com.ring.bo.ResponseBo;
import com.ring.constants.PressureUnitType;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.SavingServiceImpl;
import com.ring.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstantsBo.SAVING+UrlConstantsBo.USER_ID)
public class SavingController {
  
  @Autowired
  SavingServiceImpl savingServiceImpl;
  
  @RequestMapping(
      value = UrlConstantsBo.SAVINGDATA + UrlConstantsBo.INITIALPRESSURE +
      UrlConstantsBo.TARGETPRESSURE+UrlConstantsBo.PRESSUREUNIT_TYPE,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseBo> getSavingData(
      @PathVariable("initialPressure") double initialPressure,
      @PathVariable("targetPressure") double targetPressure,
      @PathVariable("pressureUnitType") PressureUnitType pressureUnitType,
      @PathVariable("userId") int userId) throws RingException {
    return new ResponseEntity<>( ResponseUtil.genericResponseData
        (RingMessageCode.RM_6000.name(),savingServiceImpl.getSavingData(initialPressure, targetPressure, userId, pressureUnitType)), HttpStatus.OK);
  }

}
