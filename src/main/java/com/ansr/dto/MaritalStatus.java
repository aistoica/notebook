package com.ansr.dto;

public enum MaritalStatus {

	MARRIED("married"),
	NOTMARRIED("not married");
	
	private String value;
	
	private MaritalStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
