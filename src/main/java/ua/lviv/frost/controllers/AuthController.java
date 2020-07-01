package ua.lviv.frost.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.frost.dto.JwtAuthenticationResponse;
import ua.lviv.frost.dto.LoginRequest;
import ua.lviv.frost.dto.UserRequest;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<UserResponse> register(@RequestBody UserRequest userRequest) {

        return new ResponseEntity<>(authService.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {

        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }
}
