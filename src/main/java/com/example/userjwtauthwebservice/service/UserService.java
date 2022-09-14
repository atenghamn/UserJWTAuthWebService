package com.example.userjwtauthwebservice.service;

import com.example.userjwtauthwebservice.dto.userResponse;
import com.example.userjwtauthwebservice.entities.User;
import com.example.userjwtauthwebservice.exception.NotFoundException;
import com.example.userjwtauthwebservice.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepository;

     public UserService(IUserRepository userRepository){
         this.userRepository = userRepository;
     }


     public  User getById(int id){
         return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User not found %s", id)));
     }

}
