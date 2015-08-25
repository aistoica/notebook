package com.ansr.dto;

import org.springframework.data.annotation.Id;

public class Contact {

/*    @Id
    private String id;*/
    
	private String telephone1;
	private String telephone2;
	private String email1;
	private String email2;
	
	public Contact() {}
	
	public Contact(String telephone1, String telephone2, String email1, String email2) {
		this.telephone1 = telephone1;
		this.telephone2 = telephone2;
		this.email1 = email1;
		this.email2 = email2;
	}

	public String getEmail1() {
		return email1;
	}

	public String getEmail2() {
		return email2;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}


	@Override
	public String toString() {
		return "Contact [telephone1=" + telephone1 + ", telephone2="
				+ telephone2 + ", email1=" + email1 + ", email2=" + email2
				+ "]";
	}
	
	
	
}
