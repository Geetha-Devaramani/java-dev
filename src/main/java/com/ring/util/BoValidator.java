package com.ring.util;

import com.ring.exceptions.RingException;

import java.util.Objects;

public abstract class BoValidator {

  public void validateObject(Object object, RingException ex)
      throws RingException {
    if (Objects.isNull(object)) {
      throw ex;
    }

  }

  public void validateString(String string, RingException ex)
      throws RingException {
    if (string == null || string.replace(" ", "").isEmpty()) {
      throw ex;
    }
  }

}
