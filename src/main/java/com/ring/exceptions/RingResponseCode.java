package com.ring.exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration for Response Code.
 */

public enum RingResponseCode {

  NOT_FOUND(HttpStatus.NOT_FOUND), CONFLICT(HttpStatus.CONFLICT), FORBIDDEN(
      HttpStatus.FORBIDDEN), UNAUTHORIZED(
          HttpStatus.UNAUTHORIZED), OK(
              HttpStatus.OK), UNPROCESSABLE(
                  HttpStatus.UNPROCESSABLE_ENTITY), DB_ERROR(
                      HttpStatus.INTERNAL_SERVER_ERROR), REDIRECT(
                          HttpStatus.FOUND), PAYMENT_REQUIRED(
                              HttpStatus.PAYMENT_REQUIRED), SERVER_ERROR(
                                  HttpStatus.INTERNAL_SERVER_ERROR), BAD_REQUEST(
                                      HttpStatus.BAD_REQUEST);

  HttpStatus httpStatus;

  Map<RingResponseCode, HttpStatus> responseCodeMap = new HashMap<RingResponseCode, HttpStatus>();

  RingResponseCode(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    responseCodeMap.put(this, httpStatus);
  }

  /**
   * Return the Http Status.
   * 
   * @param code Enum for which status has to be determined.
   * @return Http Status code for the given enum.
   */

  public HttpStatus getHttpStatusFromEnum(RingResponseCode code) {
    return responseCodeMap.get(code);
  }

}
