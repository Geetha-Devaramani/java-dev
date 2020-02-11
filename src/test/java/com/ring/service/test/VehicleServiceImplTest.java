package com.ring.service.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.PressureBo;
import com.ring.bo.VehicleAutoDataBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;
import com.ring.dao.VehicleDao;
import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;
import com.ring.service.VehicleServiceImpl;

public class VehicleServiceImplTest {


	@Mock
	VehicleDao dao;

	@InjectMocks
	VehicleServiceImpl service;

	@BeforeMethod(alwaysRun = true)
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void addVehicleTest1() throws RingException {
		int vehicleId=1;
		int userId =1 ;

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

		VehicleBo vehicleDetails = new  VehicleBo();
		vehicleDetails.setCarMake("delata");
		vehicleDetails.setCarModel("der");
		vehicleDetails.setVehicleAutoData(vehicleAutoData);
		vehicleDetails.setDefault(true);
		vehicleDetails.setLoad(load);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vehicleId", vehicleId);

		Mockito.when(dao.addVehicle(userId, vehicleDetails)).thenReturn(map);
		assertEquals(map, service.addVehicle(userId, vehicleDetails));


	}

	@Test(expectedExceptions = RingException.class)
	public void addVehicleTest2() throws RingException {
		int userId=1;
		VehicleBo vehicleDetails = new  VehicleBo();
		Mockito.when(dao.addVehicle(userId, vehicleDetails)).thenThrow(new RingException(RingErrorCode.RA_3017, RingResponseCode.OK));
		service.addVehicle(userId, vehicleDetails);
	}



	@Test
	public void getVehicleListTest1() throws RingException {
		List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();
		List<PressureBo> pressureList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = new Object();
		Map<String, Object> map1 = new HashMap<String, Object>();
		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.4);
		pressure.setRearTyrePressure(29.4);  
		pressure.setPressureUnitType(PressureUnitType.PSI);
		pressure.setLoadType(LoadType.STANDARD);
		pressureList.add(pressure);
		map.put("load", pressureList);
		map.put("vehicleId", 1);
		map.put("vehicleName", "vehicle 1");
		map.put("registrationNumber", "GB-EBZ-5156");
		map.put("vin", "1GBA23EBZ25152341");
		map.put("carMake", "BMW");
		map.put("carModel", "X1");
		map.put("yearOfManufacture", 2009);
		map.put("isDefault", true);
		map.put("tyreSize", "16");
		vehicleList.add(map);
		Mockito.when(dao.getVehicleList(Mockito.anyInt())).thenReturn(vehicleList);
		service.getVehicleList(1);
	}

	@Test
	public void getVehicle() throws RingException {
		List<Map<String, Object>> vehicleList = new ArrayList<Map<String, Object>>();
		List<PressureBo> pressureList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = new Object();
		Map<String, Object> map1 = new HashMap<String, Object>();
		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.4);
		pressure.setRearTyrePressure(29.4);  
		pressure.setPressureUnitType(PressureUnitType.PSI);
		pressure.setLoadType(LoadType.STANDARD);
		pressureList.add(pressure);
		map.put("load", pressureList);
		map.put("vehicleId", 1);
		map.put("vehicleName", "vehicle 1");
		map.put("registrationNumber", "GB-EBZ-5156");
		map.put("vin", "1GBA23EBZ25152341");
		map.put("carMake", "BMW");
		map.put("carModel", "X1");
		map.put("yearOfManufacture", 2009);
		map.put("isDefault", true);
		map.put("tyreSize", "16");
		vehicleList.add(map);
		Mockito.when(dao.getVehicle(Mockito.anyInt())).thenReturn(map);
		service.getVehicle(1);
	}


	@Test
	public void deleteVehicle() throws RingException {
		List<PressureBo> pressureList = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		Object obj = new Object();
		Map<String, Object> map1 = new HashMap<String, Object>();
		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.4);
		pressure.setRearTyrePressure(29.4);  
		pressure.setPressureUnitType(PressureUnitType.PSI);
		pressure.setLoadType(LoadType.STANDARD);
		pressureList.add(pressure);
		map.put("load", pressureList);
		map.put("vehicleId", 1);
		map.put("vehicleName", "vehicle 1");
		map.put("registrationNumber", "GB-EBZ-5156");
		map.put("vin", "1GBA23EBZ25152341");
		map.put("carMake", "BMW");
		map.put("carModel", "X1");
		map.put("yearOfManufacture", 2009);
		map.put("isDefault", true);
		map.put("tyreSize", "16");
		Mockito.when(dao.getVehicle(Mockito.anyInt())).thenReturn(map);
		Mockito.doNothing().when(dao)
		.deleteVehicle(Mockito.anyInt(),Mockito.anyInt());
		service.deleteVehicle(1,1);
	}



}
