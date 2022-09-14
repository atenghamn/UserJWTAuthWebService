package com.example.userjwtauthwebservice;

import com.example.userjwtauthwebservice.user.entities.User;
import com.example.userjwtauthwebservice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserJwtAuthWebServiceApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(UserJwtAuthWebServiceApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("KentBeck", passwordEncoder.encode("thisIsATest"), true));
    }
}
