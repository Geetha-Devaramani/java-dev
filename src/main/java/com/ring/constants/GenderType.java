package com.ring.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GenderType {

  MALE("Male"), FEMALE("Female"), NON_BINARY("Non-Binary"), PREFER_NOT_TO_SAY(
      "Prefer Not To Say");

  private String genderType;

  private GenderType(String genderType) {
    this.genderType = genderType;
  }

  public void setGenderType(String genderType) {
    this.genderType = genderType;
  }

  public String getGenderType() {
    return genderType;
  }

 /* public static GenderType setGenderTypeUsingCustomInput(String genderType) {

    for (GenderType genderTypeRequest : GenderType.values()) {

      if (genderTypeRequest.getGenderType().equalsIgnoreCase(genderType)) {
        return genderTypeRequest;
      }

    }
    return null;

  }*/
  
  @JsonCreator
  public static GenderType fromGenderTypeString(String genderType) {

    for (GenderType genderTypeRequest : GenderType.values()) {

      if (genderTypeRequest.getGenderType().equalsIgnoreCase(genderType)) {
        return genderTypeRequest;
      }

    }
    return null;

  }

}
