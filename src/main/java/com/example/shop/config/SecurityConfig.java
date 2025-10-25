package com.example.shop.config;

import com.example.shop.entities.Role;
import com.example.shop.filters.JwtAuthentiicationFilter;
import com.example.shop.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.HttpRequestHandler;

import java.nio.file.AccessDeniedException;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthentiicationFilter jwtAuthentiicationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
public AuthenticationProvider authenticationProvider() {
   var provider = new DaoAuthenticationProvider();
   provider.setPasswordEncoder(passwordEncoder());
   provider.setUserDetailsService(userDetailsService);
   return provider;
}
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
return config.getAuthenticationManager();
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //stateless session
        //disable csrf
        //authorize
http.sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
                c->c
                        .anyRequest().authenticated())
        .addFilterBefore(jwtAuthentiicationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(c->
        {c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.setStatus(HttpStatus.FORBIDDEN.value()));
        })
;
return http.build();
    }
}
