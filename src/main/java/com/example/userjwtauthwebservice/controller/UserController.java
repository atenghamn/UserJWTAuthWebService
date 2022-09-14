package com.example.userjwtauthwebservice.controller;

import com.example.userjwtauthwebservice.dto.UserDetail;
import com.example.userjwtauthwebservice.dto.UserDetailMapper;
import com.example.userjwtauthwebservice.dto.userResponse;
import com.example.userjwtauthwebservice.entities.User;
import com.example.userjwtauthwebservice.service.AuthService;
import com.example.userjwtauthwebservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetail> getById(@PathVariable Integer id, String token) {
        if(!authService.validate(token))
            return null;

        return ResponseEntity.ok(UserDetailMapper.from(userService.getById(id)));
    }
}
