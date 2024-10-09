package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.dto.requests.AuthenticationRequest;
import com.t3h.e_commerce.dto.responses.AuthenticationResponse;
import com.t3h.e_commerce.enums.TokenType;
import com.t3h.e_commerce.entity.UserEntity;
import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.repository.UserRepository;
import com.t3h.e_commerce.service.IAuthenticationService;
import com.t3h.e_commerce.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        validateLoginRequest(request);

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> CustomExceptionHandler.notFoundException("User not found"));

        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated){
            throw CustomExceptionHandler.unauthorizedException("User not authenticated");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

        String accessToken = jwtTokenUtils.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtils.generateRefreshToken(userDetails);

        return AuthenticationResponse.builder()
                .tokenType(TokenType.BEARER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateLoginRequest(AuthenticationRequest request) {

        if (request.getEmail() == null) {
            throw CustomExceptionHandler.badRequestException("Email is required");
        }

        if (request.getPassword() == null) {
            throw CustomExceptionHandler.badRequestException("Password is required");
        }
    }
}
