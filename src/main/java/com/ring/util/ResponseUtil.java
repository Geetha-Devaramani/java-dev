package com.ring.util;

import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.exceptions.RingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;


@ControllerAdvice
public class ResponseUtil {

  private static final Logger LOGGER = LogManager.getLogger(ResponseUtil.class);

  private static Properties prop;

  @PostConstruct
  public void init() {
    prop = new Properties();
    try {

      InputStream messageProperties = RingException.class
          .getResourceAsStream("/response.properties");
      prop.load(messageProperties);
    } catch (Exception exception) {
      LOGGER.error("Property file not found or not properly loaded");
    }
  }


  public static ResponseDataBo genericResponseData(String messageCode,Object data) 
      throws RingException {
    ResponseDataBo responseDataBo = new ResponseDataBo();
    responseDataBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseDataBo.setMessage(prop.getProperty(messageCode.replace("RM_", "")));
    responseDataBo.setData(data);
    return responseDataBo;
  }


  public static ResponseBo genericResponse(String messageCode) 
      throws RingException {
    ResponseBo responseBo = new ResponseBo();
    responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
    responseBo.setMessage(prop.getProperty(messageCode.replace("RM_", "")));
    return responseBo;
  }

  public static String genericMessage(String messageCode) 
      throws RingException {
    return prop.getProperty(messageCode.replace("RM_", ""));
  }
}
