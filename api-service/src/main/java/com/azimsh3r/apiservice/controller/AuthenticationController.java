package com.azimsh3r.apiservice.controller;

import com.azimsh3r.apiservice.dto.LoginRequestDTO;
import com.azimsh3r.apiservice.dto.RegistrationRequestDTO;
import com.azimsh3r.apiservice.enums.UserStatus;
import com.azimsh3r.apiservice.exception.ValidationExceptionHandler;
import com.azimsh3r.apiservice.model.User;
import com.azimsh3r.apiservice.security.JWTUtil;
import com.azimsh3r.apiservice.service.AuthenticationDetailsService;
import com.azimsh3r.apiservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final ValidationExceptionHandler validationExceptionHandler;

    private final AuthenticationDetailsService authenticationDetailsService;

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, ValidationExceptionHandler validationExceptionHandler, AuthenticationDetailsService authenticationDetailsService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.validationExceptionHandler = validationExceptionHandler;
        this.authenticationDetailsService = authenticationDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult);
        }

        try {
            UserDetails authenticationDetails = authenticationDetailsService.loadUserByUsername(loginRequestDTO.getUsername());

            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), authenticationDetails.getPassword())) {
                return new ResponseEntity<>(Map.of("message", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
            }

            String jwt = jwtUtil.generateToken(loginRequestDTO.getUsername());

            return new ResponseEntity<>(Map.of(
                    "token", jwt,
                    "timestamp", LocalDateTime.now()
            ), HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(Map.of("message", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult);
        }

        if (userService.existsByUsername(registrationRequestDTO.getUsername())) {
            return new ResponseEntity<>(Map.of("message", "Username already exists"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(registrationRequestDTO.getEmail())) {
            return new ResponseEntity<>(Map.of("message", "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(registrationRequestDTO.getUsername());
        newUser.setEmail(registrationRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        newUser.setRole("USER");
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreated_at(LocalDateTime.now());
        newUser.setUpdated_at(LocalDateTime.now());

        userService.save(newUser);

        return new ResponseEntity<>(Map.of(
                "message", "User registered successfully",
                "timestamp", LocalDateTime.now()
        ), HttpStatus.CREATED);
    }
}
