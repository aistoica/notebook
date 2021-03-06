package com.ansr.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by astoica on 8/7/2015.
 */
public class Address {

/*    @Id
    private String id;*/

	@NotEmpty
    private String streetName;
	
	@Min(1)
    private Long streetNo;
    
    @NotEmpty
    private String country;
    
    @NotEmpty
    private String city;
    
    @NotEmpty
    private String district;
    
    @NotEmpty
    //@Pattern(regexp = "^[0-9]{6}$")
    private String zipCode;

    public Address() {}

    public Address(String streetName, Long streetNo, String city, String country, String district, String zipCode) {
        this.streetName = streetName;
        this.streetNo = streetNo;
        this.city = city;
        this.country = country;
        this.district = district;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreetName() {
        return streetName;
    }

    public Long getStreetNo() {
        return streetNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setStreetNo(Long streetNo) {
        this.streetNo = streetNo;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", streetNo=" + streetNo +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
