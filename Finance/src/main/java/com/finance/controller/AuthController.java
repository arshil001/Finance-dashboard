package com.finance.controller;

import com.finance.dto.RegisterRequest;
import com.finance.entity.User;
import com.finance.enums.Role;
import com.finance.repositories.UserRepo;
import com.finance.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.get("email"),
                        req.get("password")
                )
        );

        String token = jwtUtil.generateToken(req.get("email"));

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if ("ROLE_ADMIN".equals(request.getRole())) {
            if (userRepository.existsByRole(Role.ROLE_ADMIN)) {
                throw new RuntimeException("Admin already exists");
            }
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 🔐 Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 🎯 Assign Role
        user.setRole(Role.valueOf(request.getRole()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

}