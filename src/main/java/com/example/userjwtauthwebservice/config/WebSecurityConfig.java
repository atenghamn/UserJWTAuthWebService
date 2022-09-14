package com.example.userjwtauthwebservice.config;

import com.example.userjwtauthwebservice.auth.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and().cors();

       httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
   }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("X-XSRF-TOKEN");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Access-Control-Allow-Headers");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addAllowedHeader("Access-Control-Request-Headers");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
            return  new BCryptPasswordEncoder();
    }
}
