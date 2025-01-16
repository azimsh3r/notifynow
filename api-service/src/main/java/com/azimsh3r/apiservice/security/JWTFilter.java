package com.azimsh3r.apiservice.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.azimsh3r.apiservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    private final UserService userService;

    public JWTFilter(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaders = request.getHeader("Authorization");

        if (authHeaders != null && authHeaders.startsWith("Bearer ")) {
            String  jwt = authHeaders.substring(7);

            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authentication Failed! Invalid JWT Token!");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveData(jwt);

                    UserDetails userDetails = userService.findAllByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException exception) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authentication Failed! Invalid JWT Token!");
                } catch (RuntimeException exception) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authentication Failed! Username is not found!");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
