package com.bank.bankingapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().disable() // Allows H2 console to run in iframe
            .and()
            .csrf().disable() // Disables CSRF (Cross-Site Request Forgery) protection
            .authorizeRequests()
                .antMatchers("/h2/**").permitAll() // Allows access to H2 console and static resources without authentication
                .antMatchers("/**").permitAll()
                .antMatchers("/login/**").permitAll() // Allows access to login endpoints without authentication
                .anyRequest().authenticated() // All other endpoints require authentication
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Statelessness for session management

        return http.build();
    }
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




