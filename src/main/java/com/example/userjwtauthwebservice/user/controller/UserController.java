package com.example.userjwtauthwebservice.user.controller;

import com.example.userjwtauthwebservice.user.dto.UserDetail;
import com.example.userjwtauthwebservice.user.dto.UserDetailMapper;
import com.example.userjwtauthwebservice.auth.service.AuthService;
import com.example.userjwtauthwebservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary ="Get user", security=@SecurityRequirement(name="bearer-key"))
    public ResponseEntity<UserDetail> getById(@PathVariable Integer id, String token) {
        if(!authService.validate(token))
            return null;

        return ResponseEntity.ok(UserDetailMapper.from(userService.getById(id)));
    }
}
