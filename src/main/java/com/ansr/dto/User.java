package com.ansr.dto;

import com.ansr.dto.Address;
import org.springframework.data.annotation.Id;

/**
 * Created by astoica on 8/7/2015.
 */
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Address adress;

    public User() {}

    public User(String firstName, String lastName, Address adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress=" + adress +
                '}';
    }
}
