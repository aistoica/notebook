package com.ansr.test;

import com.ansr.dto.Address;
import com.ansr.dto.User;
import com.ansr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by astoica on 8/7/2015.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ansr.repository")
@ComponentScan(basePackages = "com.ansr")
public class Application implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        userService.print();

        while(true);
    }
}
