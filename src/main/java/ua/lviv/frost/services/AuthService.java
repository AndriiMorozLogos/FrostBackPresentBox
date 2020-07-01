package ua.lviv.frost.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.lviv.frost.dto.JwtAuthenticationResponse;
import ua.lviv.frost.dto.LoginRequest;
import ua.lviv.frost.dto.UserRequest;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.entity.AppUser;
import ua.lviv.frost.entity.enumeration.Role;
import ua.lviv.frost.exception.LoginException;
import ua.lviv.frost.repository.AppUserRepository;
import ua.lviv.frost.security.JwtTokenProvider;
import ua.lviv.frost.security.UserPrincipal;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public UserResponse registerUser(UserRequest userRequest) {

        if (appUserRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Username [username: " + userRequest.getEmail() + "] is already taken");
        }


        AppUser user = AppUser.builder()
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(Role.ROLE_BUYER)
                .phoneNumber(userRequest.getPhoneNumber())
                .firstName(userRequest.getName())
                .lastName(userRequest.getSurname())
                .build();


        log.info("Successfully registered user with [username: {}]", user.getEmail());

        AppUser save = appUserRepository.save(user);

        return UserResponse.builder()
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .firstName(userRequest.getName())
                .lastName(userRequest.getSurname())
                .id(save.getId())
                .build();
    }


    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        if(!appUserRepository.findByEmail(loginRequest.getEmail()).isPresent()){
            throw new LoginException();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [username: {}] has logged in", userPrincipal.getUsername());

        return new JwtAuthenticationResponse(jwt, userPrincipal.getId());
    }
}
