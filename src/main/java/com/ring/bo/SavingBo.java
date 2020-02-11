package com.ring.bo;

import com.ring.constants.PressureUnitType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SavingBo extends BaseBo {

  /**
   * 
   */
  private static final long serialVersionUID = -2045010289135697025L;

  private double initialPressure;
  private double targetPressure;
  private PressureUnitType pressureUnitType;

  @Value("${ring.savingMoneyPerPsi}")
  private double  moneySaving;
  @Value("${ring.savingCarbonEmissionPerPsi}")
  private double  carbonSaving;

  @Value("${ring.barConversionValue}")
  private double barConversionValue;

  @Value("${ring.kpaConversionValue}")
  private double kpaConversionValue;


  public double getInitialPressure() {
    return initialPressure;
  }

  public void setInitialPressure(double initialPressure) {
    this.initialPressure = initialPressure;
  }

  public double getTargetPressure() {
    return targetPressure;
  }

  public void setTargetPressure(double targetPressure) {
    this.targetPressure = targetPressure;
  }

  public PressureUnitType getPressureUnitType() {
    return pressureUnitType;
  }

  public void setPressureUnitType(PressureUnitType pressureUnitType) {
    this.pressureUnitType = pressureUnitType;
  }

  public double getMoneySaving() {
    return moneySaving;
  }

  public void setMoneySaving(double moneySaving) {
    this.moneySaving = moneySaving;
  }

  public double getCarbonSaving() {
    return carbonSaving;
  }

  public void setCarbonSaving(double carbonSaving) {
    this.carbonSaving = carbonSaving;
  }

  public double getBarConversionValue() {
    return barConversionValue;
  }

  public void setBarConversionValue(double barConversionValue) {
    this.barConversionValue = barConversionValue;
  }

  public double getKpaConversionValue() {
    return kpaConversionValue;
  }

  public void setKpaConversionValue(double kpaConversionValue) {
    this.kpaConversionValue = kpaConversionValue;
  }
}
