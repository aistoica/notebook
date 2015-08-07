package com.ansr.dto;

import org.springframework.data.annotation.Id;

/**
 * Created by astoica on 8/7/2015.
 */
public class Address {

    @Id
    private String id;

    private String streetName;
    private Long streetNo;
    private String city;
    private String district;
    private String zipCode;

    public Address() {}

    public Address(String streetName, Long streetNo, String city, String district, String zipCode) {
        this.streetName = streetName;
        this.streetNo = streetNo;
        this.city = city;
        this.district = district;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Long getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(Long streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipCode() {
        return zipCode;
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
}
