package com.config;

import com.security.AuthenticationFilter;
import service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenService tokenService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
                    // Disable csrf
        httpSecurity.csrf().disable()
                    // Enable cors
                    .cors()
                    .and()
                    // Set session management to stateless
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    // Allow unauthorized requests to certain endpoints
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/products/create", "/files", "/products").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/products/**", "/user/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/user/createUser").permitAll()
                    .requestMatchers(HttpMethod.GET, "/files/**", "/products/active", "/products/active/**").permitAll()
                    .requestMatchers("/login").permitAll()
                    // Authenticate all other requests
                    .anyRequest().authenticated()
                    .and()
                    // Add filter to validate tokens with every request
                    .addFilterBefore(new AuthenticationFilter(tokenService),
                                     UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
