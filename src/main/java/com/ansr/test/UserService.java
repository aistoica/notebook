package com.ansr.test;

import com.ansr.dto.Address;
import com.ansr.dto.Contact;
import com.ansr.dto.MaritalStatus;
import com.ansr.dto.Role;
import com.ansr.dto.User;
import com.ansr.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by astoica on 8/7/2015.
 */
@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void print() {
//        userRepository.deleteAll();

        Address address = new Address("Maniu", 10L, "Buc", "S6", "000999");
        Contact contact = new Contact("0752231411", "0752231411", "test@gmail.com", "test@gmail.com");
        userRepository.save(new User("John", "Doe", address, address, "AS", "574858", "2920607033444", "Romanian", MaritalStatus.NOTMARRIED, 0, contact,Role.USER));

        for(User user : userRepository.findByFirstName("firstname")) {
            System.out.println(user);
        }
    }
}
