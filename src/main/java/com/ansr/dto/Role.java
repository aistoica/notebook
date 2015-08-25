package com.ansr.dto;

public enum Role {

	ADMIN("Admin"),
	USER("User");
	
	private String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
