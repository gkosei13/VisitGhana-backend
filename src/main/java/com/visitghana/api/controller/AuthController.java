package com.visitghana.api.controller;

import com.visitghana.api.dto.AuthResponse;
import com.visitghana.api.dto.LoginRequest;
import com.visitghana.api.dto.SignUpRequest;
import com.visitghana.api.model.User;
import com.visitghana.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // SIGN UP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody SignUpRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword()));
        user.setRole("tourist");

        User saved = userRepository.save(user);

        AuthResponse response = new AuthResponse(
                saved.getUserId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getRole(),
                "Account created successfully"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        // Check if user exists
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // Check password
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        AuthResponse response = new AuthResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                "Login successful"
        );

        return ResponseEntity.ok(response);
    }
    // UPDATE PROFILE
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long userId,
            @RequestBody SignUpRequest request) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        // Check if new email is taken by someone else
        User existingUser = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if (existingUser != null &&
                !existingUser.getUserId().equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already in use by another account");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        User saved = userRepository.save(user);

        AuthResponse response = new AuthResponse(
                saved.getUserId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getRole(),
                "Profile updated successfully"
        );

        return ResponseEntity.ok(response);
    }
    // CHANGE PASSWORD
    @PutMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestBody java.util.Map<String, String> request) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        String currentPassword = request.get("currentPassword");
        String newPassword = request.get("newPassword");

        // Verify current password
        if (!passwordEncoder.matches(currentPassword,
                user.getPasswordHash())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Current password is incorrect");
        }

        // Update to new password
        user.setPasswordHash(
                passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}