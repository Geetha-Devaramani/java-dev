package com.ring.exceptions;

import com.ring.bo.ResponseBo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LogManager
      .getLogger(GlobalExceptionHandler.class);

  private Properties prop;

  @PostConstruct
  public void init() {
    prop = new Properties();
    try {
      InputStream exceptionFileProperties = RingException.class
          .getResourceAsStream("/exceptioncode.properties");
      prop.load(exceptionFileProperties);
    } catch (Exception exception) {
      LOGGER.error("Property file not found or not properly loaded");
    }
  }

  /**
   * ExceptionHandler method for RingException.
   */
  @ExceptionHandler(RingException.class)
  @ResponseBody
  public ResponseEntity<ResponseBo> handleConnectedCarException(
      RingException ex) {
    ResponseBo errorResponse = new ResponseBo();
    HttpStatus httpResponseCode = HttpStatus.BAD_REQUEST;
    try {
      RingResponseCode responseCode = ex.getResponseCode();
      httpResponseCode = responseCode.getHttpStatusFromEnum(responseCode);
      errorResponse.setCode(
          Integer.parseInt(ex.getErrorCode().name().replace("RA_", "")));
      errorResponse.setMessage(
          prop.getProperty(ex.getErrorCode().name().replace("RA_", "")));

    } catch (Exception exception) {
      LOGGER.error("Exception in Global Exception Handler", exception);
      errorResponse.setCode(500);
      errorResponse
          .setMessage("Problem occured in Global Exception Handler");
      httpResponseCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(errorResponse, httpResponseCode);
  }

}
