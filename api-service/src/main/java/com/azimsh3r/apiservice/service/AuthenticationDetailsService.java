package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.model.User;
import com.azimsh3r.apiservice.repository.UserRepository;
import com.azimsh3r.apiservice.security.AuthenticationDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticationDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User is not found!");
        }
        return new AuthenticationDetails(user.get());
    }
}
