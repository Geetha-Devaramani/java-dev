package com.ring.controller;

import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.constants.RingMessageCode;
import com.ring.constants.UrlConstantsBo;
import com.ring.exceptions.RingException;
import com.ring.service.TyrePressureService;
import com.ring.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Controller class to manage and execute the flow of TyrePressure API. Uses
 * TyrePressureService interface and its implementation class for services.
 * </p>
 */
@RestController
@RequestMapping(UrlConstantsBo.USERS + UrlConstantsBo.USER_ID
    + UrlConstantsBo.VEHICLES + UrlConstantsBo.VEHICLE_ID)
public class TyrePressureController {

  @Autowired
  TyrePressureService service;

  /**
   * method to get last inflated days for each tyre.
   */
  @RequestMapping(
      value = UrlConstantsBo.DASHBOARD,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseBo> getLatestPressureData(
      @PathVariable("userId") int userId,
      @PathVariable("vehicleId") int vehicleId) throws RingException {
    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_4000.name(),
            service.getLatestPressureData(userId, vehicleId)),
        HttpStatus.OK);
  }

  /**
   * method to get pressureData for graph.
   */
  @RequestMapping(
      value = UrlConstantsBo.TRENDS,
      method = RequestMethod.GET)
  public ResponseEntity<ResponseDataBo> getTyrePressureDataForGraph(
      @PathVariable("userId") int userId,
      @PathVariable("vehicleId") int vehicleId,
      @RequestParam("from-month-year") String fromMonthYear,
      @RequestParam("to-month-year") String toMonthYear,
      @RequestParam("tyre-type") String tyreType) throws RingException {
    return new ResponseEntity<>(
        ResponseUtil.genericResponseData(RingMessageCode.RM_4001.name(),
            service.getTyrePressureData(userId, vehicleId,
                fromMonthYear, toMonthYear, tyreType)),
        HttpStatus.OK);
  }

}
