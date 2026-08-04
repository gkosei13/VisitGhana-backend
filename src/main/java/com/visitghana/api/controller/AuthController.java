@PostMapping("/signup")
public ResponseEntity<?> signup(
        @RequestBody SignUpRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already registered");
    }

    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPasswordHash(
            passwordEncoder.encode(request.getPassword()));
    user.setRole("tourist");
    user.setIsVerified(true);

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