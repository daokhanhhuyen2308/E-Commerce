package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.dto.requests.AuthenticationRequest;
import com.t3h.e_commerce.dto.responses.AuthenticationResponse;
import com.t3h.e_commerce.entity.UserEntity;
import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.repository.UserRepository;
import com.t3h.e_commerce.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        validateLoginRequest(request);

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> CustomExceptionHandler.notFoundException("User not found"));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated){
            throw CustomExceptionHandler.unauthorizedException("User not authenticated");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword(),
                        user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getCode()))
                                .collect(Collectors.toSet()))
        );

        return AuthenticationResponse.builder()
                .isAuthenticated(authentication.isAuthenticated())
                .build();
    }

    private void validateLoginRequest(AuthenticationRequest request) {

        if (request.getUsername() == null) {
            throw CustomExceptionHandler.badRequestException("Username is required");
        }

        if (request.getPassword() == null) {
            throw CustomExceptionHandler.badRequestException("Password is required");
        }
    }
}
