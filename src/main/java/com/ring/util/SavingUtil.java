package com.ring.util;

import com.ring.bo.SavingBo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingUtil {
  
  @Autowired
  SavingBo savingBo;
  
  public double convertPressure(String pressureUnit, double pressureValue)
  {
     double convertedData = 0.0;
     
    switch (pressureUnit) {
      case "kPa":
        convertedData = (pressureValue / savingBo.getKpaConversionValue());
        break;
      case "bar":
        convertedData = (pressureValue / savingBo.getBarConversionValue());
        break;
      default:
        convertedData = pressureValue;
      }
    return convertedData;
    
  }
  
  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}

}
