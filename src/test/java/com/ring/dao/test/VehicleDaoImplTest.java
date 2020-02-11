package com.ring.dao.test;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleAutoDataBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;
import com.ring.dao.VehicleDaoImpl;
import com.ring.dao.util.QueryBuilder;
import com.ring.exceptions.RingException;
import com.ring.util.DaoUtil;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PrepareForTest(VehicleDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class VehicleDaoImplTest extends PowerMockTestCase {

  @Mock
  JdbcTemplate template;

  @Mock
  DaoUtil daoUtil;

  @Mock
  GeneratedKeyHolder keyHolder;
  
  @Mock
  QueryBuilder builder;

  @Mock
  PreparedStatement ps;

  @Mock
  PreparedStatementCreator preparedStatementCreator;

  @Mock
  Connection con;

  @InjectMocks
  VehicleDaoImpl dao;

  @BeforeMethod(
      alwaysRun = true)
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void addVehicleTest1() throws Exception {

    int vehicleId = 1;
    int userId = 13;

    KeyHolder holder = new GeneratedKeyHolder();
    List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();
    VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
    vehicleAutoData.setManufacturerId("11");
    vehicleAutoData.setmId("11");
    vehicleAutoData.setModelId("11");
    vehicleAutoData.setTyrePressureId("11");

    PressureBo pressure = new PressureBo();
    pressure.setFrontTyrePressure(28.8);
    pressure.setRearTyrePressure(30.4);
    pressure.setLoadType(LoadType.STANDARD);
    pressure.setDefaultLoad(true);
    pressure.setPressureUnitType(PressureUnitType.PSI);
    List<PressureBo> load = new ArrayList<PressureBo>();
    load.add(pressure);

    VehicleBo vehicleDetails = new VehicleBo();
    vehicleDetails.setCarMake("delata");
    vehicleDetails.setCarModel("der");
    vehicleDetails.setVehicleAutoData(vehicleAutoData);
    vehicleDetails.setRegistrationNumber("22222");
    vehicleDetails.setVin("222");
    vehicleDetails.setVehicleName("222");
    vehicleDetails.setDefault(true);
    vehicleDetails.setLoad(load);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("vehicleId", vehicleId);
    map.put("vd_vehicle_name", "2222");
    map.put("vd_registration_number", "22");
    map.put("vd_vin", "22");
    vehicleList.add(map);
    Mockito.when(daoUtil.validateIdFromTable(Mockito.anyInt(),
        Mockito.anyString(), Mockito.anyString())).thenReturn(1);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);
    Mockito.when(template.update(Mockito.any(PreparedStatementCreator.class),
        Mockito.any(KeyHolder.class))).thenAnswer(new Answer<Number>() {
          @Override
          public Number answer(InvocationOnMock invocation) throws Throwable {
            Object[] args = invocation.getArguments();
            PreparedStatementCreator arg = (PreparedStatementCreator) args[0];
            arg.createPreparedStatement(con);
            return 1;
          }
        });
    Mockito.when(con.prepareStatement(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(ps);
    Mockito.doNothing().when(ps).setInt(Mockito.anyInt(), Mockito.anyInt());
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);
    Mockito.doNothing().when(ps).setString(Mockito.anyInt(),
        Mockito.anyString());
    Map<String, Object> mapOfKeys = new HashMap<>();
    mapOfKeys.put("vd_vehical_id", 1);
    mapOfKeys.put("ad_autodata_id", 1);
    mapOfKeys.put("ps_pressure_setting_id", 1);
    PowerMockito.whenNew(GeneratedKeyHolder.class).withNoArguments()
        .thenReturn(keyHolder);
    Mockito.when(keyHolder.getKeys()).thenReturn(mapOfKeys);
    Mockito.when(template.update(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(1);

    dao.addVehicle(userId, vehicleDetails);
  }

  @Test
  public void getVehicleListTest1() throws RingException {
    List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();

    VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
    vehicleAutoData.setManufacturerId("11");
    vehicleAutoData.setmId("11");
    vehicleAutoData.setModelId("11");
    vehicleAutoData.setTyrePressureId("11");

    PressureBo pressure = new PressureBo();
    pressure.setFrontTyrePressure(28.8);
    pressure.setRearTyrePressure(30.4);
    pressure.setLoadType(LoadType.STANDARD);
    pressure.setDefaultLoad(true);
    pressure.setPressureUnitType(PressureUnitType.PSI);

    List<PressureBo> load = new ArrayList<PressureBo>();
    load.add(pressure);

    List<Map<String, Object>> presureList = new ArrayList<Map<String, Object>>();
    Map<String, Object> map1 = new HashMap<String, Object>();
    map1.put("load", load);
    presureList.add(map1);
    VehicleBo vehicleDetails = new VehicleBo();
    vehicleDetails.setCarMake("delata");
    vehicleDetails.setCarModel("der");
    vehicleDetails.setVehicleAutoData(vehicleAutoData);
    vehicleDetails.setRegistrationNumber("22222");
    vehicleDetails.setVin("222");
    vehicleDetails.setVehicleName("222");
    vehicleDetails.setDefault(true);
    vehicleDetails.setLoad(load);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("vehicleId", 2);
    map.put("vehicleName", "ss");
    map.put("registrationNumber", 1);
    map.put("vin", "vehicle 1");
    map.put("carMake", "GB-EBZ-5156");
    map.put("carModel", "1GBA23EBZ25152341");
    map.put("load", load);
    map.put("vehicleAutoData", vehicleAutoData);
    map.put("yearOfManufacture", 2009);
    map.put("isDefault", true);
    map.put("tyreSize", "16");
    vehicleList.add(map);
    vehicleList.add(map);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(presureList);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);

    dao.getVehicleList(1);
  }

  @Test
  public void getVehicleTest1() throws RingException {
    List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();

    VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
    vehicleAutoData.setManufacturerId("11");
    vehicleAutoData.setmId("11");
    vehicleAutoData.setModelId("11");
    vehicleAutoData.setTyrePressureId("11");

    PressureBo pressure = new PressureBo();
    pressure.setFrontTyrePressure(28.8);
    pressure.setRearTyrePressure(30.4);
    pressure.setLoadType(LoadType.STANDARD);
    pressure.setDefaultLoad(true);
    pressure.setPressureUnitType(PressureUnitType.PSI);

    List<PressureBo> load = new ArrayList<PressureBo>();
    load.add(pressure);

    List<Map<String, Object>> presureList = new ArrayList<Map<String, Object>>();
    Map<String, Object> map1 = new HashMap<String, Object>();
    map1.put("load", load);
    presureList.add(map1);
    VehicleBo vehicleDetails = new VehicleBo();
    vehicleDetails.setCarMake("delata");
    vehicleDetails.setCarModel("der");
    vehicleDetails.setVehicleAutoData(vehicleAutoData);
    vehicleDetails.setRegistrationNumber("22222");
    vehicleDetails.setVin("222");
    vehicleDetails.setVehicleName("222");
    vehicleDetails.setDefault(true);
    vehicleDetails.setLoad(load);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("vehicleId", 2);
    map.put("vehicleName", "ss");
    map.put("registrationNumber", 1);
    map.put("vin", "vehicle 1");
    map.put("carMake", "GB-EBZ-5156");
    map.put("carModel", "1GBA23EBZ25152341");
    map.put("load", load);
    map.put("vehicleAutoData", vehicleAutoData);
    map.put("yearOfManufacture", 2009);
    map.put("isDefault", true);
    map.put("tyreSize", "16");
    vehicleList.add(map);
    vehicleList.add(map);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(presureList);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);

    dao.getVehicle(1);
  }

  @Test()
  public void addVehicleTest2() throws Exception {
    List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();

    int vehicleId = 1;
    int userId = 13;
    VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
    vehicleAutoData.setManufacturerId("11");
    vehicleAutoData.setmId("11");
    vehicleAutoData.setModelId("11");
    vehicleAutoData.setTyrePressureId("11");

    PressureBo pressure = new PressureBo();
    pressure.setFrontTyrePressure(28.8);
    pressure.setRearTyrePressure(30.4);
    pressure.setLoadType(LoadType.STANDARD);
    pressure.setDefaultLoad(true);
    pressure.setPressureUnitType(PressureUnitType.PSI);

    List<PressureBo> load = new ArrayList<PressureBo>();
    load.add(pressure);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("vehicleId", vehicleId);
    map.put("vd_registration_number", "22");
    map.put("vd_vin", "22");

    vehicleList.add(map);

    VehicleBo vehicleDetails = new VehicleBo();
    vehicleDetails.setCarMake("delata");
    vehicleDetails.setCarModel("der");
    vehicleDetails.setVehicleAutoData(vehicleAutoData);
    vehicleDetails.setRegistrationNumber("22222");
    vehicleDetails.setVin("222");
    vehicleDetails.setVehicleName("222");
    vehicleDetails.setDefault(true);
    vehicleDetails.setLoad(load);

    Mockito.when(daoUtil.validateIdFromTable(Mockito.anyInt(),
        Mockito.anyString(), Mockito.anyString())).thenReturn(1);
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);
    Mockito.when(template.update(Mockito.any(PreparedStatementCreator.class),
        Mockito.any(KeyHolder.class))).thenAnswer(new Answer<Number>() {
          @Override
          public Number answer(InvocationOnMock invocation) throws Throwable {
            Object[] args = invocation.getArguments();
            PreparedStatementCreator arg = (PreparedStatementCreator) args[0];
            arg.createPreparedStatement(con);
            return 1;
          }
        });
    Mockito.when(con.prepareStatement(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(ps);
    Mockito.doNothing().when(ps).setInt(Mockito.anyInt(), Mockito.anyInt());
    Mockito.when(template.queryForList(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(vehicleList);
    Mockito.doNothing().when(ps).setString(Mockito.anyInt(),
        Mockito.anyString());
    Map<String, Object> mapOfKeys = new HashMap<>();
    mapOfKeys.put("vd_vehical_id", 1);
    mapOfKeys.put("ad_autodata_id", 1);
    mapOfKeys.put("ps_pressure_setting_id", 1);
    PowerMockito.whenNew(GeneratedKeyHolder.class).withNoArguments()
        .thenReturn(keyHolder);
    Mockito.when(keyHolder.getKeys()).thenReturn(mapOfKeys);
    Mockito.when(template.update(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(1);

    dao.addVehicle(userId, vehicleDetails);
  }

  @Test
  public void editVehicleTest() throws RingException {
    String GET_PRESSURE_ID = "SELECT vps_pressure_setting_id FROM "
        + "tbl_vehicle_pressure_setting WHERE vps_vehical_id = ? AND vps_pressure_type=?";
    
    VehicleBo vehicleBo = new VehicleBo();
    vehicleBo.setRegistrationNumber("897985n7892357");
    vehicleBo.setCarMake("Maruti");
    vehicleBo.setCarModel("800");
    vehicleBo.setYearOfManufacture("1997-");

    VehicleAutoDataBo autoDataBo = new VehicleAutoDataBo();
    autoDataBo.setManufacturerId("1");
    autoDataBo.setmId("2");
    autoDataBo.setModelId("3");
    autoDataBo.setTyrePressureId("4");

    List<PressureBo> list = new ArrayList<>();
    PressureBo pressureBo = new PressureBo();
    pressureBo.setDefaultLoad(true);
    pressureBo.setFrontTyrePressure(38.6);
    pressureBo.setRearTyrePressure(32.7);
    pressureBo.setTyreSize("17");
    pressureBo.setPressureUnitType(PressureUnitType.KPA);
    pressureBo.setLoadType(LoadType.STANDARD);
    list.add(pressureBo);

    vehicleBo.setLoad(list);
    vehicleBo.setVehicleAutoData(autoDataBo);

    List<Map<String, Object>> listOfVehicleName = new ArrayList<>();
    Map<String, Object> vehicleName = new HashMap<>();
    vehicleName.put("vd_vehicle_name", "Vehicle1");
    listOfVehicleName.add(vehicleName);

    Mockito.when(
        template.queryForList(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(listOfVehicleName);

    List<Map<String, Object>> listOfVehicleInfo = new ArrayList<>();
    Map<String, Object> vehicleInfo = new HashMap<>();
    vehicleName.put("vd_vehicle_name", "Car1");
    vehicleName.put("vd_registration_number", "8q237582vn8925");
    vehicleName.put("vd_vin", "89278927897349");
    listOfVehicleInfo.add(vehicleInfo);

    Mockito.when(
        template.queryForList(Mockito.anyString(), Mockito.any(Object[].class)))
        .thenReturn(listOfVehicleName);

    //QueryBuilder builder = Mockito.mock(QueryBuilder.class);
    
    Mockito
        .when(builder.getQueryAndPlaceholderDataMap(Mockito.any(Object.class),
            Mockito.anyInt(), Mockito.anyInt()))
        .thenCallRealMethod();
    
    Mockito.when(template.update(Mockito.anyString(), Mockito.any(Object[].class))).thenReturn(1).thenReturn(1).thenReturn(1).thenReturn(1);
    Mockito.when(template.queryForObject(GET_PRESSURE_ID, Integer.class, 2, "Standard")).thenReturn(1);
    Mockito.when(
        template.queryForList(Mockito.anyString(),Mockito.any(Class.class), Mockito.any(Object[].class)))
        .thenReturn(Arrays.asList(1,2,3));
    
    Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(Object[].class),Mockito.any(Class.class))).thenReturn(1);
    
    dao.editVehicle(1, 2, vehicleBo);
    
    

  }

}
