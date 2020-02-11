package com.ring.exceptions;

/**
 * Custom Exception class for Connected Car. All service layers will throw
 * ConnectedCarException for business exceptions with a pre-defined Error Code
 * and Error description. Generic exceptions from try catch block from service
 * layer to throw ConnectedCarException with RA_000 and BAD_REQUEST codes.
 * 
 * @see RingErrorCode
 * @see RingResponseCode
 * 
 */

public class RingException extends Exception {

  private static final long serialVersionUID = 1L;

  RingErrorCode errorCode;

  RingResponseCode responseCode;

  /**
   * Constructor for RingException.
   * 
   * @param errorCode Error Code
   * @param responseCode Response Code
   */

  public RingException(RingErrorCode errorCode,
      RingResponseCode responseCode) {

    this.errorCode = errorCode;
    this.responseCode = responseCode;
  }

  /**
   * Constructor for RingException.
   * 
   * @param errorCode Error Code
   * @param responseCode Response Code
   * @param message Message to be set.
   */

  public RingException(RingErrorCode errorCode,
      RingResponseCode responseCode, String message) {

    super(message);
    this.errorCode = errorCode;
    this.responseCode = responseCode;
  }

  /**
   * Constructor for RingException.
   * 
   * @param errorCode Error Code
   * @param responseCode Response Code
   * @param message Message to be set.
   * @param cause Throwable object.
   */

  public RingException(RingErrorCode errorCode,
      RingResponseCode responseCode,
      String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.responseCode = responseCode;
  }

  /**
   * Constructor for RingException.
   * 
   * @param errorCode Error Code
   * @param responseCode Response Code
   * @param message Message.
   * @param cause Throwable cause.
   * @param enableSuppression Enable Suppression.
   * @param writableStackTrace Writable Stack Trace.
   */

  public RingException(RingErrorCode errorCode,
      RingResponseCode responseCode,
      String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCode = errorCode;
    this.responseCode = responseCode;
  }

  /**
   * Constructor for RingException.
   * 
   * @param errorCode Error Code
   * @param responseCode Response Code
   * @param cause Throwable Cause.
   */
  public RingException(RingErrorCode errorCode,
      RingResponseCode responseCode, Throwable cause) {
    super(cause);
    this.errorCode = errorCode;
    this.responseCode = responseCode;
  }

  public RingErrorCode getErrorCode() {
    return errorCode;
  }

  public RingResponseCode getResponseCode() {
    return responseCode;
  }

  public void setErrorCode(RingErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public void setResponseCode(RingResponseCode responseCode) {
    this.responseCode = responseCode;
  }

}
