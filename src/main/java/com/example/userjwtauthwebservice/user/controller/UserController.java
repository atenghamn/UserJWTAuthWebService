package com.example.userjwtauthwebservice.user.controller;

import com.example.userjwtauthwebservice.auth.domain.TokenWrapper;
import com.example.userjwtauthwebservice.auth.dto.SimpleResponse;
import com.example.userjwtauthwebservice.post.dto.PostDetail;
import com.example.userjwtauthwebservice.post.dto.PostDetailMapper;
import com.example.userjwtauthwebservice.post.service.PostService;
import com.example.userjwtauthwebservice.user.dto.CreateUser;
import com.example.userjwtauthwebservice.user.dto.UserDetail;
import com.example.userjwtauthwebservice.user.dto.UserDetailMapper;
import com.example.userjwtauthwebservice.auth.service.AuthService;
import com.example.userjwtauthwebservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdmin;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @Operation(summary ="Get user", security=@SecurityRequirement(name="bearer-key"))
    public ResponseEntity<UserDetail> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(UserDetailMapper.from(userService.getById(id)));
    }

    @PostMapping
    @Operation(summary="Create a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<UserDetail> create(@RequestBody CreateUser user, @RequestHeader(value="Authorization") String bearer){
        authorizeAdmin(bearer, "Create User");
        return ResponseEntity.ok(UserDetailMapper.from(userService.create(user)));
    }


    @PostMapping("/{id}")
    @Operation(summary="Uppdate a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<UserDetail> create(@PathVariable Integer id, @RequestBody CreateUser user, @RequestHeader(value="Authorization") String bearer){
        authorizeAdmin(bearer, "Update User");
        var userId = new TokenWrapper(bearer).getUserId();
       if(Objects.equals(userId, id))
            return ResponseEntity.ok(UserDetailMapper.from(userService.update(id, user)));

       return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<SimpleResponse> delete(@PathVariable Integer id,
                                                 @RequestHeader(value="Authorization") String bearer){
        authorizeAdmin(bearer, "Remove a user");
        userService.delete(id);
        return ResponseEntity.ok(new SimpleResponse(Instant.now(), "Dead and gone..."));
    }

    @GetMapping("/posts?userId={id}")
    @Operation(summary = "Get all posts made by user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<List<PostDetail>> getPosts(@PathVariable Integer id,
                                                     @RequestHeader(value = "Authorization") String bearer){
        authorizeAdmin(bearer, "Get posts");
        return ResponseEntity.ok(PostDetailMapper.from(postService.getAll(id)));

    }


}
