package com.example.userjwtauthwebservice.user.service;

import com.example.userjwtauthwebservice.exception.NotFoundException;
import com.example.userjwtauthwebservice.user.entities.User;
import com.example.userjwtauthwebservice.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    UserService userService;
    @Mock PasswordEncoder passwordEncoder;

    @BeforeEach
    void init (@Mock UserRepository userRepository){
        userService = new UserService(userRepository, passwordEncoder);

        Mockito.lenient().when(userRepository.findUserByUsername("Kalle")).thenReturn(Optional.ofNullable(User.builder()
                .username("Kalle")
                .isAdministrator(false)
                .id(99)
                .build()));

        Mockito.lenient().when((userRepository.findById(99))).thenReturn(Optional.ofNullable(User.builder()
                        .username("Kalle")
                        .isAdministrator(false)
                        .id(99)
                        .build()));
    }

    @Test
    public void getUserByName(){
        Assertions.assertEquals(userService.getByUsername("Kalle").getUsername(), "Kalle");
    }

    @Test
    public void getById(){
        Assertions.assertEquals(userService.getById(99).getUsername(), "Kalle");
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound(){
        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class, () -> userService.getById(200));

        Assertions.assertTrue(thrown.getMessage().contains("not found"));
    }


}
