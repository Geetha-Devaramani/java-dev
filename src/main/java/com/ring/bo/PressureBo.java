package com.ring.bo;

import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;

public class PressureBo extends BaseBo {
  
  /**
   * 
   */
  private static final long serialVersionUID = -5182151100724870300L;
  
  private Integer pressureSettingId;
  private Double frontTyrePressure;
  private Double rearTyrePressure;
  private LoadType loadType;
  private String tyreSize;
  private PressureUnitType pressureUnitType;
  private int createdUserId;
  private int lastMOdifiedUserId;
  private Boolean defaultLoad;


  public String getTyreSize() {
    return tyreSize;
  }
  public void setTyreSize(String tyreSize) {
    this.tyreSize = tyreSize;
  }

  public Integer getPressureSettingId() {
    return pressureSettingId;
  }
  public void setPressureSettingId(Integer pressureSettingId) {
    this.pressureSettingId = pressureSettingId;
  }
  public Double getFrontTyrePressure() {
    return frontTyrePressure;
  }
  public void setFrontTyrePressure(Double frontTyrePressure) {
    this.frontTyrePressure = frontTyrePressure;
  }
  public Double getRearTyrePressure() {
    return rearTyrePressure;
  }
  public void setRearTyrePressure(Double rearTyrePressure) {
    this.rearTyrePressure = rearTyrePressure;
  }
  public LoadType getLoadType() {
    return loadType;
  }
  public void setLoadType(LoadType loadType) {
    this.loadType = loadType;
  }
  public PressureUnitType getPressureUnitType() {
    return pressureUnitType;
  }
  public void setPressureUnitType(PressureUnitType pressureUnitType) {
    this.pressureUnitType = pressureUnitType;
  }
  public int getCreatedUserId() {
    return createdUserId;
  }
  public void setCreatedUserId(int createdUserId) {
    this.createdUserId = createdUserId;
  }
  public int getLastMOdifiedUserId() {
    return lastMOdifiedUserId;
  }
  public void setLastMOdifiedUserId(int lastMOdifiedUserId) {
    this.lastMOdifiedUserId = lastMOdifiedUserId;
  }
  public Boolean getDefaultLoad() {
    return defaultLoad;
  }
  public void setDefaultLoad(Boolean defaultLoad) {
    this.defaultLoad = defaultLoad;
  }
 


}