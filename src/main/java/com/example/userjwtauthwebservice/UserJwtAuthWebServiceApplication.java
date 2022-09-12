package com.example.userjwtauthwebservice;

import com.example.userjwtauthwebservice.entities.User;
import com.example.userjwtauthwebservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserJwtAuthWebServiceApplication implements CommandLineRunner {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(UserJwtAuthWebServiceApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Kent Beck", passwordEncoder.encode("thisIsATest")));
    }
}
