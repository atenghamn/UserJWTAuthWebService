package com.example.userjwtauthwebservice.user.service;

import com.example.userjwtauthwebservice.user.entities.User;
import com.example.userjwtauthwebservice.exception.NotFoundException;
import com.example.userjwtauthwebservice.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

     public UserService(UserRepository userRepository){
         this.userRepository = userRepository;
     }


     public  User getById(int id){
         return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User not found %s", id)));
     }
     public User getByUsername(String username){
         return userRepository.findUserByUsername(username).orElseThrow();
     }

}
