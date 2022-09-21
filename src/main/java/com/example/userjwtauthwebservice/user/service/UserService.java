package com.example.userjwtauthwebservice.user.service;

import com.example.userjwtauthwebservice.user.dto.CreateUser;
import com.example.userjwtauthwebservice.user.entities.User;
import com.example.userjwtauthwebservice.exception.NotFoundException;
import com.example.userjwtauthwebservice.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

     public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
         this.userRepository = userRepository;
         this.passwordEncoder = passwordEncoder;
     }


     public  User getById(int id){
         return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User not found %s", id)));
     }
     public User getByUsername(String username){
         return userRepository.findUserByUsername(username).orElseThrow();
     }

     public User create (CreateUser entity){
         return userRepository.save(
                 User.builder()
                         .username(entity.username())
                         .password(passwordEncoder.encode(entity.password()))
                         .isAdministrator(entity.isAdministrator())
                         .build()
         );
     }

     public User update(Integer id, CreateUser user){
         var entity = userRepository.findById(id)
                 .orElseThrow();
         entity.setUsername(user.username());
         entity.setPassword(user.password());
         entity.setAdministrator(user.isAdministrator());
         return userRepository.save(entity);
     }

     public void delete(Integer id) {userRepository.deleteById(id);}

}
