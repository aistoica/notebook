package com.ansr.repository;

import com.ansr.dto.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by astoica on 8/7/2015.
 */
@Configuration
public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByFirstName(String firstName);
}
