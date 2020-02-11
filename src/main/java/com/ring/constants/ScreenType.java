package com.ring.constants;

public enum ScreenType {

	EULA_SCREEN(1),LOGIN_SCREEN(2), HOME_SCREEN(3),ADD_VEHICLE_SCREEN(4);

	private int screenCode;

	private ScreenType(int screenCode) {
		this.screenCode = screenCode;
	}

	public int getScreenCode() {
		return screenCode;
	}

	public static ScreenType getScreenType(
			int screenCode) {

		for (ScreenType screenType : ScreenType.values()) {

			if (screenType.getScreenCode() == screenCode) {
				return screenType;
			}

		}
		return null;

	}

}
