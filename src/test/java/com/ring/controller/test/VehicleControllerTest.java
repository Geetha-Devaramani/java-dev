package com.ring.controller.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ring.bo.PressureBo;
import com.ring.bo.ResponseBo;
import com.ring.bo.ResponseDataBo;
import com.ring.bo.VehicleAutoDataBo;
import com.ring.bo.VehicleBo;
import com.ring.constants.LoadType;
import com.ring.constants.PressureUnitType;
import com.ring.controller.VehicleController;
import com.ring.service.VehicleService;
import com.ring.util.PressureBoValidator;
import com.ring.util.ResponseUtil;
import com.ring.util.VehicleBoValidator;

@PrepareForTest({ResponseUtil.class})
@PowerMockIgnore("javax.management.*")
public class VehicleControllerTest extends PowerMockTestCase {

	@Mock
	VehicleService service;

	@Mock
	VehicleBoValidator vehicleBoValidator;

	@Mock
	PressureBoValidator pressureBoValidator;

	@InjectMocks
	VehicleController controller;

	@Mock
	Properties prop;

	@BeforeMethod(
			alwaysRun = true)
	public void initMocks() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	ResponseDataBo responseDataBo = new ResponseDataBo();
	ResponseBo responseBo = new ResponseBo();

	@Test
	public void addVehicleTest1() throws Exception {

		int vehicleId = 1;

		VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
		vehicleAutoData.setManufacturerId("11");
		vehicleAutoData.setmId("11");
		vehicleAutoData.setModelId("11");
		vehicleAutoData.setTyrePressureId("11");
		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.8);
		pressure.setRearTyrePressure(30.4);
		pressure.setLoadType(LoadType.STANDARD);
		pressure.setPressureUnitType(PressureUnitType.PSI);
		List<PressureBo> load = new ArrayList<PressureBo>();
		load.add(pressure);
		VehicleBo vehicleDetails = new VehicleBo();
		vehicleDetails.setCarMake("delata");
		vehicleDetails.setCarModel("der");
		vehicleDetails.setVehicleAutoData(vehicleAutoData);
		vehicleDetails.setDefault(true);
		vehicleDetails.setLoad(load);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("vehicleId", vehicleId);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("vehicle doesn't exists");

		Mockito.doNothing().when(vehicleBoValidator)
		.validateForVehicleAddition(Mockito.any(VehicleBo.class));
		Mockito
		.when(
				service.addVehicle(Mockito.anyInt(), Mockito.any(VehicleBo.class)))
		.thenReturn(map);

		responseDataBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseDataBo.setMessage("Vehicle added successfully");
		responseDataBo.setData(map);


		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponseData(Mockito.anyString(), Mockito.any(Object.class))).thenReturn(responseDataBo);

		ResponseEntity<ResponseDataBo> response = new ResponseEntity<ResponseDataBo>(
				responseDataBo, HttpStatus.OK);
		assertEquals(response.toString(),
				controller.addVehicle(1, new VehicleBo()).toString());

	}

	@Test
	public void  editVehicleTest() throws Exception {

		int vehicleId = 1;

		VehicleAutoDataBo vehicleAutoData = new VehicleAutoDataBo();
		vehicleAutoData.setManufacturerId("11");
		vehicleAutoData.setmId("11");
		vehicleAutoData.setModelId("11");
		vehicleAutoData.setTyrePressureId("11");
		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.8);
		pressure.setRearTyrePressure(30.4);
		pressure.setLoadType(LoadType.STANDARD);
		pressure.setPressureUnitType(PressureUnitType.PSI);
		List<PressureBo> load = new ArrayList<PressureBo>();
		load.add(pressure);
		VehicleBo vehicleDetails = new VehicleBo();
		vehicleDetails.setCarMake("delata");
		vehicleDetails.setCarModel("der");
		vehicleDetails.setVehicleAutoData(vehicleAutoData);
		vehicleDetails.setDefault(true);
		vehicleDetails.setLoad(load);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		map.put("vehicleId", vehicleId);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("Valid format of year_month is required");

		Mockito.doNothing().when(vehicleBoValidator)
		.validateForVehicleAddition(Mockito.any(VehicleBo.class));
		Mockito.doNothing().when(service).editVehicle(Mockito.anyInt(),Mockito.anyInt(),Mockito.any(VehicleBo.class));

		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("Vehicle details updated successfully");



		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);

		ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
				responseBo, HttpStatus.OK);
		assertEquals(response.toString(),
				controller.editVehicle(1,1, new VehicleBo()).toString());

	}

	@Test
	public void  editPressureSettingsTest() throws Exception {

		int vehicleId = 1;


		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.8);
		pressure.setRearTyrePressure(30.4);
		pressure.setLoadType(LoadType.STANDARD);
		pressure.setPressureUnitType(PressureUnitType.PSI);
		List<PressureBo> load = new ArrayList<PressureBo>();
		load.add(pressure);
		VehicleBo vehicleDetails = new VehicleBo();
		vehicleDetails.setCarMake("delata");
		vehicleDetails.setCarModel("der");

		vehicleDetails.setDefault(true);
		vehicleDetails.setLoad(load);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		map.put("vehicleId", vehicleId);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("Valid format of year_month is required");

		Mockito.doNothing().when(pressureBoValidator)
		.validateForPressureSettingsUpdate(Mockito.any(PressureBo.class));
		Mockito.doNothing().when(service).editPressureSettings(Mockito.anyInt(),Mockito.anyInt(),Mockito.any(PressureBo.class));

		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("Pressure settings updated successfully");



		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);

		ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
				responseBo, HttpStatus.OK);
		assertEquals(response.toString(),
				controller.editPressureSettings(1,1, new PressureBo()).toString());

	}

	@Test
	public void  updateDefaultstatusTest() throws Exception {

		int vehicleId = 1;


		PressureBo pressure = new PressureBo();
		pressure.setFrontTyrePressure(28.8);
		pressure.setRearTyrePressure(30.4);
		pressure.setLoadType(LoadType.STANDARD);
		pressure.setPressureUnitType(PressureUnitType.PSI);
		List<PressureBo> load = new ArrayList<PressureBo>();
		load.add(pressure);
		VehicleBo vehicleDetails = new VehicleBo();
		vehicleDetails.setCarMake("delata");
		vehicleDetails.setCarModel("der");

		vehicleDetails.setDefault(true);
		vehicleDetails.setLoad(load);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		map.put("vehicleId", vehicleId);
		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("Valid format is required");

		Mockito.doNothing().when(service).updateDefaultstatus(Mockito.anyInt(),Mockito.anyInt());

		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("Vehicle default status updated successfully");



		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);

		ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
				responseBo, HttpStatus.OK);
		assertEquals(response.toString(),
				controller.updateDefaultstatus(1,1).toString());

	}

	@Test
	public void getVehicleListTest1() throws Exception {
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

		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("vehicle doesn't exists");
		Mockito.when(service.getVehicleList(Mockito.anyInt())).thenReturn(vehicleList);
		responseDataBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseDataBo.setMessage("Vehicle list fetched successfully");
		responseDataBo.setData(vehicleList);

		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponseData(Mockito.anyString(), Mockito.any(Object.class))).thenReturn(responseDataBo);

		ResponseEntity<ResponseDataBo> response = new ResponseEntity<ResponseDataBo>(
				responseDataBo, HttpStatus.OK);


		assertEquals(response.toString(),controller.getVehicleList(1).toString());
	}

	@Test
	public void getVehicleTest1() throws Exception {

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


		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("vehicle doesn't exists");
		Mockito.when(service.getVehicle(Mockito.anyInt())).thenReturn(map);
		responseDataBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseDataBo.setMessage("Vehicle Details");
		responseDataBo.setData(map);

		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponseData(Mockito.anyString(), Mockito.any(Object.class))).thenReturn(responseDataBo);

		ResponseEntity<ResponseDataBo> response = new ResponseEntity<ResponseDataBo>(
				responseDataBo, HttpStatus.OK);


		assertEquals(response.toString(),controller.getVehicle(1).toString());
	}

	@Test
	public void deleteVehicleTest1() throws Exception {




		Mockito.when(prop.getProperty(Mockito.anyString()))
		.thenReturn("vehicle deletion failed");
		Mockito.doNothing().when(service).deleteVehicle(Mockito.anyInt(),Mockito.anyInt());

		responseBo.setCode(Integer.parseInt(HttpStatus.OK.toString()));
		responseBo.setMessage("Vehicle Deleted successfully");


		PowerMockito.mockStatic(ResponseUtil.class);
		Mockito.when(ResponseUtil.genericResponse(Mockito.anyString())).thenReturn(responseBo);

		ResponseEntity<ResponseBo> response = new ResponseEntity<ResponseBo>(
				responseBo, HttpStatus.OK);

		controller.deleteVehicle(1,1).toString();

	}
}