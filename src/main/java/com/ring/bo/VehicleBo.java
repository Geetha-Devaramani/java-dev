package com.ring.bo;

import com.ring.constants.Status;

import java.sql.Timestamp;
import java.util.List;

public class VehicleBo extends UserBo {

  /**
   * 
   */
  private static final long serialVersionUID = -755632970953494448L;

  private Integer vehicleId;
  private String vehicleName;
  private String registrationNumber;
  private String carMake;
  private String carModel;
  private String vin;
  private String bodyStyle;
  private String yearOfManufacture;
  boolean isDefault;
  private List<PressureBo> load;
  private Status status;
  private int createdUserId;
  private Timestamp createdDateTime;
  private int lastMOdifiedUserId;
  private Timestamp lastMOdifiedDateTime;

  private VehicleAutoDataBo vehicleAutoData;

  public Integer getVehicleId() {
    return vehicleId;
  }
  public void setVehicleId(Integer vehicleId) {
    this.vehicleId = vehicleId;
  }
  public String getVehicleName() {
    return vehicleName;
  }
  public void setVehicleName(String vehicleName) {
    this.vehicleName = vehicleName;
  }
  public String getRegistrationNumber() {
    return registrationNumber;
  }
  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }
  public String getCarMake() {
    return carMake;
  }
  public void setCarMake(String carMake) {
    this.carMake = carMake;
  }
  public String getCarModel() {
    return carModel;
  }
  public void setCarModel(String carModel) {
    this.carModel = carModel;
  }
  public String getVin() {
    return vin;
  }
  public void setVin(String vin) {
    this.vin = vin;
  }
  public String getYearOfManufacture() {
    return yearOfManufacture;
  }
  public void setYearOfManufacture(String yearOfManufacture) {
    this.yearOfManufacture = yearOfManufacture;
  }
  public boolean isDefault() {
    return isDefault;
  }
  public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public int getCreatedUserId() {
    return createdUserId;
  }
  public void setCreatedUserId(int createdUserId) {
    this.createdUserId = createdUserId;
  }
  public Timestamp getCreatedDateTime() {
    return createdDateTime;
  }
  public void setCreatedDateTime(Timestamp createdDateTime) {
    this.createdDateTime = createdDateTime;
  }
  public int getLastMOdifiedUserId() {
    return lastMOdifiedUserId;
  }
  public void setLastMOdifiedUserId(int lastMOdifiedUserId) {
    this.lastMOdifiedUserId = lastMOdifiedUserId;
  }
  public Timestamp getLastMOdifiedDateTime() {
    return lastMOdifiedDateTime;
  }
  public void setLastMOdifiedDateTime(Timestamp lastMOdifiedDateTime) {
    this.lastMOdifiedDateTime = lastMOdifiedDateTime;
  }

  public VehicleAutoDataBo getVehicleAutoData() {
    return vehicleAutoData;
  }
  public void setVehicleAutoData(VehicleAutoDataBo vehicleAutoData) {
    this.vehicleAutoData = vehicleAutoData;
  }
  public List<PressureBo> getLoad() {
    return load;
  }
  public void setLoad(List<PressureBo> load) {
    this.load = load;
  }
  public String getBodyStyle() {
    return bodyStyle;
  }
  public void setBodyStyle(String bodyStyle) {
    this.bodyStyle = bodyStyle;
  }
  
}
