package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.model.User;
import com.azimsh3r.apiservice.repository.UserRepository;
import com.azimsh3r.apiservice.security.AuthenticationDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public UserDetails findAllByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty()) {
            throw new RuntimeException("User with this username is not found!");
        }
        return new AuthenticationDetails(user.get());
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void deleteUser(Integer id, User currentUser) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!currentUser.getId().equals(id) && !currentUser.getRole().equals("ADMIN")) {
            throw new SecurityException("Unauthorized to delete this user");
        }
        userRepository.delete(userToDelete);
    }

    public User updateUser(Integer id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
