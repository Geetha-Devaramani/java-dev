package com.ring.constants;

public enum Status {
	ACTIVE("Active"), INACTIVE("In-Active"), DELETED("Deleted");

	String status;

	Status(String status) {
		this.status = status;

	}



	public String getStatus() {
		return status;
	}

	/**
	 * Get user status
	 * 
	 * @param statusType string ex:Active
	 * @return UserStatus ex:ACTIVE.
	 */
	public static Status getStatus(
			String statusType) {

		for (Status statusValue : Status.values()) {

			if (statusValue.getStatus()==statusType) {
				return statusValue;
			}

		}

		return null;

	}

}