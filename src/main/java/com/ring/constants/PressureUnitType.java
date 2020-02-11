package com.ring.constants;

public enum PressureUnitType {


	PSI("psi"), KPA("kPa"),BAR("bar");
	private String presureType;


	private PressureUnitType(String presureType) {
		this.presureType = presureType;
	}

	public String getPresureType() {
		return presureType;
	}

	public static PressureUnitType getPresureType(
			String presureType) {

		for (PressureUnitType presureTypeRequest : PressureUnitType.values()) {

			if (presureTypeRequest.getPresureType() == presureType) {
				return presureTypeRequest;
			}

		}
		return null;

	}
}
