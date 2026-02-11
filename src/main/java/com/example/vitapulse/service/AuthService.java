package com.example.vitapulse.service;

import com.example.vitapulse.dto.auth.RegisterRequest;
import com.example.vitapulse.dto.auth.RegisterResponse;
import com.example.vitapulse.entity.User;
import com.example.vitapulse.entity.enums.Role;
import com.example.vitapulse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        User saved = userRepository.save(user);

        return new RegisterResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getRole()
        );
    }
}
