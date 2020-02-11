package com.ring.dao;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleAutoDataBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.AppConstant;
import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;
import com.ring.constants.Status;
import com.ring.dao.util.QueryBuilder;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.util.DaoUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class VehicleDaoImpl implements VehicleDao {

  @Autowired
  JdbcTemplate jdbctemplate;

  @Autowired
  DaoUtil daoUtil;

  @Autowired
  QueryBuilder queryBuilder;

  private static final String ADD_VEHICLE_QUERY_ONE = "INSERT INTO tbl_vehicle_details(vd_user_id,vd_vehicle_name,"
      + "vd_registration_number, vd_vin, vd_vehicle_maker,vd_vehicle_model, "
      + "vd_vehicle_year_of_manf,"
      + "vd_is_default, vd_status, vd_created_user_id,vd_created_datetime, "
      + "vd_lastmodified_user_id,vd_lastmodified_datetime,vd_body_style) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?)";
  private static final String ADD_PRESSURE_SETTING = "INSERT INTO tbl_pressure_setting(ps_front_tyre_pressur, ps_rear_tyre_pressur,"
      + "ps_pressure_type,ps_tyre_size,ps_pressure_units,"
      + " ps_created_user_id, ps_lastmodified_user_id,isdefault)VALUES (?, ?, ?, ?, ?, ?, ? , ?)";

  private static final String ADD_VEHICLE_PRESSURE_SETTING = "INSERT INTO tbl_vehicle_pressure_setting(vps_pressure_setting_id,"
      + "vps_vehical_id,vps_created_user_id,vps_created_datetime, "
      + "vps_lastmodified_user_id, vps_lastmodified_datetime, vps_pressure_type) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_DEFAULT_VEHICLE_ID_QUERY = "SELECT vd_vehical_id FROM tbl_vehicle_details WHERE vd_is_default=TRUE AND vd_status ='Active' AND vd_user_id=? ";

  private static final String PUT_DEFAULT_STATUS_VEHICLE_QUERY = "update tbl_vehicle_details set vd_is_default=?,vd_lastmodified_datetime=? where vd_vehical_id=?";

  private static final String DELETE_VEHICLE = "UPDATE tbl_vehicle_details SET "
      + "vd_status=?,vd_is_default=?,vd_lastmodified_user_id=?,"
      + "vd_lastmodified_datetime=? WHERE vd_vehical_id =?";

  private static final String GET_PRESSURE_ID = "SELECT vps_pressure_setting_id FROM "
      + "tbl_vehicle_pressure_setting WHERE vps_vehical_id = ? AND vps_pressure_type=?";

  private static final String GET_LIST_OF_VEHICLES = "SELECT vd_vehical_id \"vehicleId\", vd_vehicle_name \"vehicleName\","
      + "vd_registration_number \"registrationNumber\", vd_vin \"vin\", vd_vehicle_maker \"carMake\", vd_vehicle_model \"carModel\", "
      + "vd_vehicle_year_of_manf \"yearOfManufacture\", vd_body_style \"bodyStyle\", "
      + "vd_is_default \"isDefault\" FROM tbl_vehicle_details where vd_user_id = ? AND vd_status ='Active'";

  private static final String VEHICLE_ENTRY = "SELECT vd_vehicle_name,vd_registration_number,vd_vin"
      + " FROM tbl_vehicle_details WHERE vd_user_id=? AND vd_status ='Active'";

  private static final String GET_VEHICLE = "SELECT vd_vehical_id \"vehicleId\","
      + "vd_vehicle_name \"vehicleName\","
      + "vd_registration_number \"registrationNumber\", vd_vin \"vin\", vd_vehicle_maker \"carMake\", vd_vehicle_model \"carModel\", "
      + "vd_vehicle_year_of_manf \"yearOfManufacture\", vd_body_style \"bodyStyle\", "
      + "vd_is_default \"isDefault\" FROM tbl_vehicle_details where vd_vehical_id = ? AND vd_status ='Active'";

  private static final String GET_VEHICLE_NAME = "SELECT vd_vehicle_name from tbl_vehicle_details WHERE "
      + "vd_vehicle_name LIKE 'Vehicle%' "
      + "AND (vd_status ='Active' AND vd_user_id= ?) "
      + "ORDER BY vd_vehicle_name; ";

  private static final String GET_VEHICLE_LOAD_DATA = " SELECT ps.ps_front_tyre_pressur"
      + " \"frontTyrePressure\", ps.ps_rear_tyre_pressur \"rearTyrePressure\",ps.ps_tyre_size \"tyreSize\", ps.ps_pressure_type \"loadType\", "
      + "ps.ps_pressure_units \"pressureUnitType\" , ps.isdefault \"defaultLoad\" FROM tbl_pressure_setting ps INNER JOIN "
      + "tbl_vehicle_pressure_setting vps ON "
      + "vps.vps_pressure_setting_id = ps.ps_pressure_setting_id WHERE vps.vps_vehical_id = ? ORDER BY ps.isdefault DESC";

  private static final String GET_OTHER_VEHICLE_LIST = "SELECT vd_vehicle_name, "
      + "vd_registration_number, vd_vin FROM tbl_vehicle_details "
      + "WHERE vd_vehical_id <> ? AND vd_user_id = ? AND vd_status ='Active'";

  private static final String VEHICLE_AUTODATA_QUERY = "INSERT INTO tbl_autodata_field(ad_vehical_id, "
      + "ad_autodata_manufacturer_id, ad_autodata_model_id, "
      + "ad_mid, ad_tyre_pressure_id) VALUES (?, ?, ?, ?, ?)";

  private static final String GET_VEHICLE_AUTODATA = "SELECT  ad_autodata_manufacturer_id  \"manufacturerId\", "
      + "ad_autodata_model_id  \"modelId\",ad_mid  \"mId\", ad_tyre_pressure_id  \"tyrePressureId\""
      + "FROM tbl_autodata_field WHERE ad_vehical_id = ?";

  private static final String UPDATE_VEHICLE_AUTODATA = "UPDATE tbl_autodata_field SET "
      + "ad_autodata_manufacturer_id=?, ad_autodata_model_id=?, ad_mid=?, "
      + "ad_tyre_pressure_id=? WHERE ad_vehical_id=?;";

  private static final String GET_DEFAULT_LOAD_PRESSURE_ID = "SELECT ps_pressure_setting_id "
      + "FROM tbl_pressure_setting WHERE ps_pressure_setting_id IN (?,?,?) "
      + "AND isdefault = 't'";

  private static final String UPDATE_DEFAULT_LOAD_STATUS = "UPDATE tbl_pressure_setting SET "
      + "ps_lastmodified_user_id=?, isdefault=? WHERE ps_pressure_setting_id=?";

  private static final String GET_PRESSURE_IDS = "SELECT vps_pressure_setting_id FROM "
      + "tbl_vehicle_pressure_setting WHERE vps_vehical_id = ?";

  @Transactional
  @Override
  public Map<String, Object> addVehicle(int userId,
      VehicleBo vehicleDetails) throws RingException {
    Map<String, Object> map = new HashMap<>();
    KeyHolder holder = new GeneratedKeyHolder();
    try {
      daoUtil.validateIdFromTable(userId, "usr_user_id", "tbl_user");
      jdbctemplate.update(insertVehicle(userId,
          validateDefaultVehicle(userId, vehicleDetails)), holder);
      int vehicleId = (int) holder.getKeys().get("vd_vehical_id");
      map.put("vehicleId", vehicleId);
      if (vehicleDetails.getVehicleAutoData() != null) {
        insertVehicleAutoData(vehicleDetails, vehicleId);
        insertPresuresetting(userId, vehicleDetails, vehicleId);
      } else {
        insertPresuresetting(userId, vehicleDetails, vehicleId);
      }

    } catch (InvalidDataAccessApiUsageException ex) {
      throw ex;
    } catch (DataAccessException e) {
      throw new RingException(RingErrorCode.RA_3017, RingResponseCode.OK);
    }

    return map;

  }

  private void insertPresuresetting(int userId, VehicleBo vehicleDetails,
      int vehicleId) throws RingException {

    PressureBo mockPressureData = new PressureBo();
    mockPressureData.setFrontTyrePressure(AppConstant.DEFAULT_VALUE);
    mockPressureData.setRearTyrePressure(AppConstant.DEFAULT_VALUE);
    mockPressureData.setPressureUnitType(PressureUnitType.PSI);
    mockPressureData.setTyreSize(AppConstant.NA);
    Arrays.asList(LoadType.values()).stream()
        .filter(loadType -> vehicleDetails.getLoad().stream()
            .allMatch(pressureBo -> {
              if (pressureBo.getLoadType().name()
                  .equalsIgnoreCase(loadType.name())) {
                int pressureSettingId = updatePressure(userId, pressureBo);
                updateVehiclePressureSetting(userId, vehicleId,
                    pressureSettingId, loadType);
                return false;
              }
              return true;
            }))
        .forEach(loadType -> {
          mockPressureData.setLoadType(loadType);
          mockPressureData.setDefaultLoad(false);
          int pressureSettingId = updatePressure(userId, mockPressureData);
          updateVehiclePressureSetting(userId, vehicleId, pressureSettingId,
              loadType);
        });

  }

  private VehicleBo validateDefaultVehicle(int userId, VehicleBo vehicleDetails)
      throws RingException {
    List<Map<String, Object>> listOfVehicles = jdbctemplate
        .queryForList(VEHICLE_ENTRY, new Object[]{userId});
    if (listOfVehicles.size() > AppConstant.ZERO) {
      validateDuplicateData(userId, vehicleDetails, listOfVehicles);
      vehicleDetails.setDefault(false);
    } else {
      vehicleDetails.setDefault(true);
    }
    return vehicleDetails;
  }

  private PreparedStatementCreator insertVehicle(int userId,
      VehicleBo vehicleDetails) throws RingException {
    return new PreparedStatementCreator() {
      PreparedStatement ps = null;
      @Override
      public PreparedStatement createPreparedStatement(Connection connection)
          throws SQLException {
        try {
          ps = connection.prepareStatement(
              ADD_VEHICLE_QUERY_ONE,
              Statement.RETURN_GENERATED_KEYS);
          ps.setInt(1, userId);
          String vehicleName = vehicleDetails.getVehicleName();
          if (vehicleName == null) {
            Map<String, String> vehicleMap = getVehicleName(userId);
            Map.Entry<String, String> entry = vehicleMap.entrySet().iterator()
                .next();
            vehicleName = entry.getKey();
          }
          ps.setString(2, vehicleName);
          ps.setString(3, vehicleDetails.getRegistrationNumber());
          ps.setString(4, vehicleDetails.getVin());
          ps.setString(5, vehicleDetails.getCarMake());
          ps.setString(6, vehicleDetails.getCarModel());
          ps.setString(7, vehicleDetails.getYearOfManufacture());
          ps.setBoolean(8, vehicleDetails.isDefault());
          ps.setString(9, Status.ACTIVE.getStatus());
          ps.setInt(10, userId);
          ps.setString(11, DaoUtil.getDate());
          ps.setInt(12, userId);
          ps.setString(13, DaoUtil.getDate());
          ps.setString(14, vehicleDetails.getBodyStyle());

        } catch (Exception ex) {
          throw ex;

        }
        return ps;
      }
    };
  }

  private int insertVehicleAutoData(VehicleBo vehicleAutoDataDetails,
      int vehicleId) throws RingException {

    KeyHolder holder = new GeneratedKeyHolder();
    jdbctemplate.update(new PreparedStatementCreator() {
      PreparedStatement ps = null;
      @Override
      public PreparedStatement createPreparedStatement(Connection connection)
          throws SQLException {
        try {
          ps = connection.prepareStatement(
              VEHICLE_AUTODATA_QUERY,
              Statement.RETURN_GENERATED_KEYS);
          ps.setInt(1, vehicleId);
          ps.setString(2, vehicleAutoDataDetails.getVehicleAutoData()
              .getManufacturerId());
          ps.setString(3,
              vehicleAutoDataDetails.getVehicleAutoData().getModelId());
          ps.setString(4,
              vehicleAutoDataDetails.getVehicleAutoData().getmId());
          ps.setString(5, vehicleAutoDataDetails.getVehicleAutoData()
              .getTyrePressureId());
        } catch (Exception ex) {
          throw ex;

        }
        return ps;
      }
    }, holder);
    return (int) holder.getKeys().get("ad_autodata_id");
  }

  private int updatePressure(int userId,
      PressureBo pressureDetails) {
    KeyHolder holder = new GeneratedKeyHolder();

    jdbctemplate.update(new PreparedStatementCreator() {
      PreparedStatement ps = null;
      @Override
      public PreparedStatement createPreparedStatement(Connection connection)
          throws SQLException {
        ps = connection.prepareStatement(ADD_PRESSURE_SETTING,
            Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, pressureDetails.getFrontTyrePressure());
        ps.setDouble(2, pressureDetails.getRearTyrePressure());
        ps.setString(3, pressureDetails.getLoadType().getLoadType());
        ps.setString(4, pressureDetails.getTyreSize());

        ps.setString(5,
            pressureDetails.getPressureUnitType().getPresureType());
        ps.setInt(6, userId);
        ps.setInt(7, userId);
        ps.setBoolean(8, pressureDetails.getDefaultLoad());
        return ps;
      }
    }, holder);

    return (int) holder.getKeys().get("ps_pressure_setting_id");

  }

  private void updateVehiclePressureSetting(int userId, int vehicleId,
      int pressureSettingId, LoadType loadType) {
    KeyHolder holder = new GeneratedKeyHolder();

    jdbctemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection)
          throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            ADD_VEHICLE_PRESSURE_SETTING,
            Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, pressureSettingId);
        ps.setInt(2, vehicleId);
        ps.setInt(3, userId);
        ps.setString(4, DaoUtil.getDate());
        ps.setInt(5, userId);
        ps.setString(6, DaoUtil.getDate());
        ps.setString(7, loadType.getLoadType());
        return ps;
      }
    }, holder);

  }
  private void validateDuplicateData(int userId,
      VehicleBo vehicleDetails, List<Map<String, Object>> listOfVehicles)
      throws RingException {
    if (listOfVehicles.size() < AppConstant.FIVE) {
      validateFields(vehicleDetails, listOfVehicles);
    } else {
      throw new RingException(RingErrorCode.RA_3014,
          RingResponseCode.OK);

    }
  }

  private Map<String, String> getVehicleName(int userId) {
    List<Map<String, Object>> listOfVehicles = jdbctemplate
        .queryForList(GET_VEHICLE_NAME, new Object[]{userId});
    Map<String, String> map = new LinkedHashMap<>();
    map.put("Vehicle1", "Vehicle1");
    map.put("Vehicle2", "Vehicle2");
    map.put("Vehicle3", "Vehicle3");
    map.put("Vehicle4", "Vehicle4");
    map.put("Vehicle5", "Vehicle5");
    for (Map<String, Object> vehicleMap : listOfVehicles) {
      if (map.containsKey(vehicleMap.get("vd_vehicle_name"))) {
        map.remove(vehicleMap.get("vd_vehicle_name"));
      }
    }
    return map;

  }

  static RingException ringException;

  private void validateFields(VehicleBo vehicleDetails,
      List<Map<String, Object>> listOfVehicles) throws RingException {

    ringException = null;

    Map<Boolean, Runnable> map = new HashMap<>();
    map.put(listOfVehicles.stream()
        .anyMatch(vehicleInfoMap -> String
            .valueOf(vehicleInfoMap.get("vd_vin"))
            .equalsIgnoreCase(vehicleDetails.getVin())),
        () -> ringException = new RingException(RingErrorCode.RA_3008,
            RingResponseCode.OK));
    map.put(listOfVehicles.stream()
        .anyMatch(vehicleInfoMap -> {
          String regNo = String
              .valueOf(vehicleInfoMap.get("vd_registration_number"));
          return regNo != null && vehicleDetails.getRegistrationNumber() != null
              && regNo.trim().replace(" ", "")
                  .equalsIgnoreCase(
                      vehicleDetails.getRegistrationNumber().trim()
                          .replace(" ", ""));
        }), () -> ringException = new RingException(RingErrorCode.RA_3007,
            RingResponseCode.OK));
    map.put(listOfVehicles.stream()
        .anyMatch(vehicleInfoMap -> String
            .valueOf(vehicleInfoMap.get("vd_vehicle_name"))
            .equalsIgnoreCase(vehicleDetails.getVehicleName())),
        () -> ringException = new RingException(RingErrorCode.RA_3006,
            RingResponseCode.OK));

    map.entrySet().stream().forEach(entryObj -> {
      if (entryObj.getKey()) {
        map.get(entryObj.getKey()).run();
      }
    });

    if (!Objects.isNull(ringException)) {
      throw ringException;
    }

  }

  @Override
  public List<Map<String, Object>> getVehicleList(int userId) {
    List<Map<String, Object>> listOfVehicles = jdbctemplate
        .queryForList(GET_LIST_OF_VEHICLES, userId);
    for (Map<String, Object> vehicleMap : listOfVehicles) {
      int vehicleId = (Integer) vehicleMap.get("vehicleId");
      getAutoData(vehicleId, vehicleMap);
      vehicleMap.put("load",
          jdbctemplate.queryForList(GET_VEHICLE_LOAD_DATA, vehicleId));
    }
    return listOfVehicles;
  }

  @Override
  public Map<String, Object> getVehicle(int vehicleId) {
    List<Map<String, Object>> listOfVehicles = jdbctemplate
        .queryForList(GET_VEHICLE, vehicleId);
    for (Map<String, Object> vehicleMap : listOfVehicles) {
      getAutoData(vehicleId, vehicleMap);
      vehicleMap.put("load",
          jdbctemplate.queryForList(GET_VEHICLE_LOAD_DATA, vehicleId));
    }
    return listOfVehicles.get(AppConstant.ZERO);
  }

  private void getAutoData(int vehicleId, Map<String, Object> vehicleMap) {
    List<Map<String, Object>> autoDataList = jdbctemplate
        .queryForList(GET_VEHICLE_AUTODATA, vehicleId);
    if (!autoDataList.isEmpty()) {
      vehicleMap.put("vehicleAutoData",
          jdbctemplate.queryForList(GET_VEHICLE_AUTODATA, vehicleId)
              .get(AppConstant.ZERO));
    } else {
      vehicleMap.put("vehicleAutoData",
          null);
    }
  }

  @Override
  public void updateDefaultstatus(int userId, int vehicleId) {
    int defaultVehicleId = jdbctemplate.queryForObject(
        GET_DEFAULT_VEHICLE_ID_QUERY,
        new Object[]{userId}, Integer.class);
    jdbctemplate.update(PUT_DEFAULT_STATUS_VEHICLE_QUERY, false,
        DaoUtil.getDate(),
        defaultVehicleId);
    jdbctemplate.update(PUT_DEFAULT_STATUS_VEHICLE_QUERY, true,
        DaoUtil.getDate(),
        vehicleId);

  }

  @Override
  public void deleteVehicle(int vehicleId, int userId) throws RingException {
    int defaultVehicleId = 0;
    try {
      defaultVehicleId = jdbctemplate.queryForObject(
          GET_DEFAULT_VEHICLE_ID_QUERY,
          new Object[]{userId}, Integer.class);

      if (vehicleId != defaultVehicleId) {
        jdbctemplate.update(DELETE_VEHICLE, "In-Active", false, userId,
            DaoUtil.getDate(),
            vehicleId);
      } else {
        defaultVehicleId = getDefaultVehicleId(vehicleId, userId,
            defaultVehicleId);
        jdbctemplate.update(DELETE_VEHICLE, "In-Active", false, userId,
            DaoUtil.getDate(),
            vehicleId);
        jdbctemplate.update(PUT_DEFAULT_STATUS_VEHICLE_QUERY, true,
            DaoUtil.getDate(),
            defaultVehicleId);
      }
    } catch (DataAccessException e) {
      throw new RingException(RingErrorCode.RA_3020,
          RingResponseCode.OK);
    }

  }

  private int getDefaultVehicleId(int vehicleId, int userId,
      int defaultVehicleId) {
    List<Map<String, Object>> listOfVehicles = jdbctemplate
        .queryForList(GET_LIST_OF_VEHICLES, userId);
    for (Map<String, Object> vehicleMap : listOfVehicles) {
      int id = (int) vehicleMap.get("vehicleId");
      if (id != vehicleId) {
        defaultVehicleId = id;
        break;
      }
    }
    return defaultVehicleId;
  }

  @Override
  @Transactional
  public void editVehicle(int userId, int vehicleId, VehicleBo vehicleDetails)
      throws RingException {
    if (vehicleDetails.getVehicleName() == null) {
      Map<String, String> vehicleMap = getVehicleName(userId);
      Map.Entry<String, String> entry = vehicleMap.entrySet().iterator()
          .next();
      vehicleDetails.setVehicleName(entry.getKey());
    }

    validateFields(vehicleDetails,
        jdbctemplate.queryForList(GET_OTHER_VEHICLE_LIST, vehicleId, userId));

    editInfo(vehicleDetails, userId, vehicleId);

    editAutoData(vehicleId, vehicleDetails);

    if (vehicleDetails.getLoad() != null && !vehicleDetails.getLoad().isEmpty())
      vehicleDetails.getLoad().stream().forEach(
          pressureBo -> editPressureSettings(userId, vehicleId, pressureBo));

  }

  private void editAutoData(int vehicleId, VehicleBo vehicleDetails) {
    if (!Objects.isNull(vehicleDetails.getVehicleAutoData())) {
      VehicleAutoDataBo autoDataBo = vehicleDetails.getVehicleAutoData();
      jdbctemplate.update(UPDATE_VEHICLE_AUTODATA,
          autoDataBo.getManufacturerId(), autoDataBo.getModelId(),
          autoDataBo.getmId(), autoDataBo.getTyrePressureId(), vehicleId);
    }
  }

  @Override
  public void editPressureSettings(int userId, int vehicleId,
      PressureBo vehicleDetails) {
    int pressureSettingId = jdbctemplate.queryForObject(GET_PRESSURE_ID,
        Integer.class, vehicleId, vehicleDetails.getLoadType().getLoadType());
    if (vehicleDetails.getDefaultLoad() != null
        && vehicleDetails.getDefaultLoad()) {
      changePressureLoadDefaultStatus(userId, vehicleId, pressureSettingId);
    }
    editInfo(vehicleDetails, userId, pressureSettingId);
  }

  private void changePressureLoadDefaultStatus(int userId, int vehicleId,
      int pressureSettingId) {
    List<Integer> listOfPressureIds = jdbctemplate.queryForList(
        GET_PRESSURE_IDS, Integer.class, vehicleId);
    Integer defaultLoadPressureId = jdbctemplate.queryForObject(
        GET_DEFAULT_LOAD_PRESSURE_ID, listOfPressureIds.toArray(),
        Integer.class);

    jdbctemplate.update(UPDATE_DEFAULT_LOAD_STATUS, userId, false,
        defaultLoadPressureId);
    jdbctemplate.update(UPDATE_DEFAULT_LOAD_STATUS, userId, true,
        pressureSettingId);

  }

  private void editInfo(Object obj, int userId, int objRelatedId) {
    Map<String, Object> getQueryAndPlaceholderDataMap = queryBuilder
        .getQueryAndPlaceholderDataMap(obj, userId, objRelatedId);
    if (!getQueryAndPlaceholderDataMap.isEmpty()) {
      jdbctemplate.update(
          String.class.cast(getQueryAndPlaceholderDataMap.get("query")),
          Object[].class
              .cast(getQueryAndPlaceholderDataMap.get("placeholderData")));
    }
  }
}