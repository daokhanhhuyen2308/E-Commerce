package com.t3h.e_commerce.configuration;

import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.service.impl.CustomUserDetailsService;
import com.t3h.e_commerce.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtils,
                                   CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        //get the jwt token from Authorization
        String authHeader = response.getHeader("Authorization");

        String token = null;
        String userEmail = null;

        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7).trim();
            try{
                userEmail = jwtTokenUtils.getUserEmailFromToken(token);
            }
            catch (SignatureException ex){
                throw CustomExceptionHandler.unauthorizedException("Invalid token");
            }
            catch (ExpiredJwtException ex){
                throw CustomExceptionHandler.unauthorizedException("Token has expired");
            }
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            if (jwtTokenUtils.validate(userDetails, token)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //set the authentication context
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);


    }

}
