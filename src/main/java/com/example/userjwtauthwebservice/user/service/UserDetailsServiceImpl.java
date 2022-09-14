package com.example.userjwtauthwebservice.user.service;

import com.example.userjwtauthwebservice.user.entities.User;
import com.example.userjwtauthwebservice.user.repository.UserRepository;
import com.example.userjwtauthwebservice.auth.domain.AuthorityName;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){this.userRepository = userRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Couldn't find user with username %s", username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(authoritiesHandler(user.isAdministrator())))
                .build();

    }

    public String authoritiesHandler(boolean isAdmin){
        if (!isAdmin)
            return AuthorityName.ROLE_ADMIN.toString();

        return AuthorityName.ROLE_USER.toString();
    }
}