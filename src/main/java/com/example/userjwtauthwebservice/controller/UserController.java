package com.example.userjwtauthwebservice.controller;

import com.example.userjwtauthwebservice.dto.userResponse;
import com.example.userjwtauthwebservice.entities.User;
import com.example.userjwtauthwebservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/{id}")
    public userResponse getById(@PathVariable Integer id) {return userService.getById(id);}
}
