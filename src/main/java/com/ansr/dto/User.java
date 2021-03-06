package com.ansr.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 * Created by astoica on 8/7/2015.
 */
public class User {

	@Id
	private String id;

	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@Valid
	private Address currentAddress;
	
	@Valid
	private Address birthAddress;
	
	@NotEmpty
	private String idSeries;
	
	@NotEmpty
	//@Pattern(regexp = "^\\d+$")
	private String idNumber;
	
	@NotEmpty
	//@Pattern(regexp = "^\\d+$")
	@Size(min= 13, max=13)
	private String idPersonalCode;
	
	@NotEmpty
	private String nationality;
	
	@NotEmpty
	private String maritalStatus;
	
	@NotNull
	@Min(0)
	private int noOfChildren;
	
	@Valid
	private Contact contactInfo;

	private String photo;
	
	private Set<Payment> payments;
	private Set<Disability> disabilities;

	public User() {
		payments = new HashSet<>();
		disabilities = new HashSet<>();
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String firstName, String lastName, Address currentAddress, Address birthAddress, String idSeries,
			String idNumber, String idPersonalCode, String nationality, String maritalStatus, int noOfChildren,
			Contact contactInfo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.currentAddress = currentAddress;
		this.birthAddress = birthAddress;
		this.idSeries = idSeries;
		this.idNumber = idNumber;
		this.idPersonalCode = idPersonalCode;
		this.nationality = nationality;
		this.maritalStatus = maritalStatus;
		this.noOfChildren = noOfChildren;
		this.contactInfo = contactInfo;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public Contact getContactInfo() {
		return contactInfo;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public Set<Disability> getDisabilities() {
		return disabilities;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getId() {
		return id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public String getIdPersonalCode() {
		return idPersonalCode;
	}

	public String getIdSeries() {
		return idSeries;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setDisabilities(Set<Disability> disabilities) {
		this.disabilities = disabilities;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setIdPersonalCode(String idPersonalCode) {
		this.idPersonalCode = idPersonalCode;
	}

	public void setIdSeries(String idSeries) {
		this.idSeries = idSeries;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", currentAddress="
				+ currentAddress + ", birthAddress=" + birthAddress + ", idSeries=" + idSeries + ", idNumber="
				+ idNumber + ", idPersonalCode=" + idPersonalCode + ", nationality=" + nationality + ", maritalStatus="
				+ maritalStatus + ", noOfChildren=" + noOfChildren + ", contactInfo=" + contactInfo + "]";
	}

}
