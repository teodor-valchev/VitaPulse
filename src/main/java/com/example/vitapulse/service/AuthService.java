package com.example.vitapulse.service;

import com.example.vitapulse.dto.auth.LoginResponse;
import com.example.vitapulse.dto.auth.LoginRequest;
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
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        public LoginResponse login(LoginRequest loginRequest) {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            String token = jwtService.generateToken(user.getEmail());
            return new LoginResponse(token);
        }

}

