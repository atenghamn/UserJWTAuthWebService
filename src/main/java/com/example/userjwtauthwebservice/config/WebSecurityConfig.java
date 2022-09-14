package com.example.userjwtauthwebservice.config;

import com.example.userjwtauthwebservice.auth.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@EnableWebMvc
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;


    public WebSecurityConfig(UserDetailsService userDetailsService, JWTAuthorizationFilter jwtAuthorizationFilter){
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

   @Bean
   SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
       httpSecurity
               .httpBasic().disable()
               .csrf().disable()
               .authorizeRequests(auth -> auth
                       .antMatchers("/v3/**").permitAll()
                       .antMatchers("/swagger-ui.html").permitAll()
                       .antMatchers("/swagger-ui/**").permitAll()
                       .antMatchers("/swagger-resources/**").permitAll()
                       .antMatchers(HttpMethod.POST,"/api/auth").permitAll()
                       .antMatchers(HttpMethod.GET,"/api/user/{id}").permitAll()
                       .anyRequest().authenticated())
               .userDetailsService(userDetailsService)
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

       httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
   }

    @Bean
    PasswordEncoder passwordEncoder(){
            return  new BCryptPasswordEncoder();
    }
}
