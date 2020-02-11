package com.ring.dao.util;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleBo;
import com.ring.util.DaoUtil;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class QueryBuilder {
  private StringBuilder queryPart1;

  private StringBuilder queryPart2;

  private StringBuilder queryPart3;

  private StringBuilder builder;

  private List<Object> dataList;

  private Map<String, Object> queryAndPlaceholderDataMap;

  private void init() {
    queryPart1 = new StringBuilder();
    queryPart2 = new StringBuilder();
    queryPart3 = new StringBuilder();
    builder = new StringBuilder();
    dataList = new ArrayList<>();
    queryAndPlaceholderDataMap = new HashMap<>();
  }

  public Map<String, Object> getQueryAndPlaceholderDataMap(Object obj,
      int userId,
      int objRelatedId) {
    init();
    buildMapUsingobjectInfo(obj, userId, objRelatedId);
    updateQueryAndDataMap(builder, dataList);
    return queryAndPlaceholderDataMap;
  }

  private void buildMapUsingobjectInfo(Object obj, int userId,
      int objRelatedId) {
    if (obj instanceof VehicleBo) {
      buildQueryUsingVehicleInfo(VehicleBo.class.cast(obj), userId,
          objRelatedId);
    }
    if (obj instanceof PressureBo) {
      buildQueryUsingPressureSettingsInfo(PressureBo.class.cast(obj), userId,
          objRelatedId);
    }

  }

  private void buildQueryUsingVehicleInfo(VehicleBo vehicleBo, int userId,
      int vehicleId) {
    queryPart1.append("UPDATE tbl_vehicle_details SET ");
    buildQueryUsingNullCheck(queryPart2, vehicleBo.getVehicleName(),
        "vd_vehicle_name", " = ?, ");
    buildQuery(queryPart2, vehicleBo.getRegistrationNumber(),
        "vd_registration_number", " = ?, ");
    buildQuery(queryPart2, vehicleBo.getVin(), "vd_vin",
        " = ?, ");
    buildQueryUsingNullCheck(queryPart2, vehicleBo.getCarMake(),
        "vd_vehicle_maker",
        " = ?, ");
    buildQueryUsingNullCheck(queryPart2, vehicleBo.getCarModel(),
        "vd_vehicle_model",
        " = ?, ");
    buildQuery(queryPart2, vehicleBo.getBodyStyle(),
        "vd_body_style",
        " = ?, ");
    buildQuery(queryPart2, vehicleBo.getYearOfManufacture(),
        "vd_vehicle_year_of_manf", " = ?, ");
    buildQuery(queryPart2, userId, "vd_lastmodified_user_id", " = ?, ");
    buildQuery(queryPart2, DaoUtil.getDate(), "vd_lastmodified_datetime",
        " = ? ");
    buildQuery(queryPart3, vehicleId, "WHERE vd_vehical_id", " = ? ");

  }

  private void buildQueryUsingPressureSettingsInfo(PressureBo pressureBo,
      int userId, int pressureSettingsId) {
    queryPart1.append("UPDATE tbl_pressure_setting SET ");
    buildQueryUsingNullCheck(queryPart2, pressureBo.getFrontTyrePressure(),
        "ps_front_tyre_pressur", " = ?, ");
    buildQueryUsingNullCheck(queryPart2, pressureBo.getRearTyrePressure(),
        "ps_rear_tyre_pressur", " = ?, ");
    buildQueryUsingNullCheck(queryPart2, pressureBo.getTyreSize(),
        "ps_tyre_size",
        " = ?, ");
    if (!Objects.isNull(pressureBo.getPressureUnitType())) {
      buildQueryUsingNullCheck(queryPart2,
          pressureBo.getPressureUnitType().getPresureType(),
          "ps_pressure_units", " = ?, ");
    }
    buildQuery(queryPart2, userId, "ps_lastmodified_user_id", " = ? ");
    buildQuery(queryPart3, pressureSettingsId, "WHERE ps_pressure_setting_id",
        " = ? ");
    buildQuery(queryPart3, pressureBo.getLoadType().getLoadType(),
        "AND ps_pressure_type", " = ? ");

  }

  private void updateQueryAndDataMap(StringBuilder builder,
      List<Object> dataList) {
    if (queryPart2.length() != 0) {
      queryPart1.append(queryPart2);
      queryPart1.append(queryPart3);
      queryAndPlaceholderDataMap.put("query", String.valueOf(queryPart1));
      queryAndPlaceholderDataMap.put("placeholderData", dataList.toArray());
    }
  }

  private void buildQueryUsingNullCheck(StringBuilder builder, Object obj,
      String fieldToAppend,
      String stringToAppend) {
    if (!Objects.isNull(obj)) {
      buildQuery(builder, obj, fieldToAppend, stringToAppend);
    }
  }

  private void buildQuery(StringBuilder builder, Object obj,
      String fieldToAppend,
      String stringToAppend) {
    builder.append(fieldToAppend);
    builder.append(stringToAppend);
    dataList.add(obj);
  }
}
