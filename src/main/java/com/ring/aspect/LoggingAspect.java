package com.ring.aspect;

import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging Aspect Log method entry and exit Log method parameters and return
 * argument. Execution time
 * 
 */
@Component
@Aspect
public class LoggingAspect {

  Logger logger = LogManager.getLogger(LoggingAspect.class);

  @AfterThrowing(
      pointcut = "execution(* com.ring.controller.*.*(..))",
      throwing = "ex")
  public void exeptionTest(JoinPoint joinPoint, Throwable ex) throws Exception {

    if (ex instanceof RingException) {
      RingException ringEx = RingException.class.cast(ex);
      if (Arrays.asList("RA_5000", "RA_5001", "RA_5002", "RA_5003", "RA_5004")
          .contains(ringEx.getErrorCode().name())) {
        logger.error("Exception coming from "
            + joinPoint.getTarget().getClass().getName() + ":"
            + joinPoint.getSignature().getName() + " ------>"
            + ((RingException) ex).getErrorCode().name());
        throw new RingException(RingErrorCode.RA_5005,
            RingResponseCode.OK);
      }

      else {
        logger.error("Exception coming from "
            + joinPoint.getTarget().getClass().getName() + ":"
            + joinPoint.getSignature().getName() + " ------>"
            + ((RingException) ex).getErrorCode().name());
      }

    } else {
      logger.error("Exception coming from "
          + joinPoint.getTarget().getClass().getName() + ":"
          + joinPoint.getSignature().getName() + " ------>" + ex.getMessage());
      throw new RingException(RingErrorCode.RA_1000,
          RingResponseCode.OK);
    }
  }

}
