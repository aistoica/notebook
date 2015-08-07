package com.ansr.test;

import com.ansr.dto.Address;
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
        userRepository.save(new User("firstname", "lastName", address));

        for(User user : userRepository.findByFirstName("firstname")) {
            System.out.println(user);
        }
    }
}
