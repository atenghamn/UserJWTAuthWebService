package com.example.userjwtauthwebservice.user.controller;

import com.example.userjwtauthwebservice.auth.domain.TokenWrapper;
import com.example.userjwtauthwebservice.auth.dto.SimpleResponse;
import com.example.userjwtauthwebservice.post.dto.PostDetail;
import com.example.userjwtauthwebservice.post.service.PostService;
import com.example.userjwtauthwebservice.user.dto.CreateUser;
import com.example.userjwtauthwebservice.user.dto.UserDetail;
import com.example.userjwtauthwebservice.user.dto.UserDetailMapper;
import com.example.userjwtauthwebservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdmin;
import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdminForDataManipulation;

@Tag(name="User", description = "Information about users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;


    @GetMapping("/{id}")
    @Operation(summary ="Get user", security=@SecurityRequirement(name="bearer-key"))
    public ResponseEntity<UserDetail> getById(@PathVariable Integer id, @RequestHeader(value="Authorization") String bearer) {
        authorizeAdmin(bearer, "Get User");
        return ResponseEntity.ok(UserDetailMapper.from(userService.getById(id)));
    }

    @PostMapping
    @Operation(summary="Create a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<UserDetail> create(@RequestBody CreateUser user, @RequestHeader(value="Authorization") String bearer){
        authorizeAdmin(bearer, "Create User");
        return ResponseEntity.ok(UserDetailMapper.from(userService.create(user)));
    }


    @PutMapping("/{id}")
    @Operation(summary="Update a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<UserDetail> create(@PathVariable Integer id, @RequestBody CreateUser user, @RequestHeader(value="Authorization") String bearer){
        if(!authorizeAdminForDataManipulation(bearer, "Update user")){
            var userId = new TokenWrapper(bearer).getUserId();
            if(!Objects.equals(userId, id)) { return null;}
        }
            return ResponseEntity.ok(UserDetailMapper.from(userService.update(id, user)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<SimpleResponse> delete(@PathVariable Integer id,
                                                 @RequestHeader(value="Authorization") String bearer){
        authorizeAdmin(bearer, "Remove a user");
        userService.delete(id);
        return ResponseEntity.ok(new SimpleResponse(Instant.now(), "Dead and gone..."));
    }


    @GetMapping("/{id}/posts")
    @Operation(summary = "Get all posts made by user", security = {@SecurityRequirement(name="bearer-key")})
    public ResponseEntity<List<PostDetail>> getPosts(@PathVariable Integer id,
                                                     @RequestHeader(value = "Authorization") String bearer){

        if(!authorizeAdminForDataManipulation(bearer, "Update user")){
            var userId = new TokenWrapper(bearer).getUserId();
            if(!Objects.equals(userId, id))
                return null;
        }
        return ResponseEntity.ok(postService.getAll(id));
    }


}
