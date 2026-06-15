package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "🔐 Authentication", description = "Login to get JWT token")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Returns a JWT token. Use it as: Authorization: Bearer <token>"
    )
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Admin user
        if ("admin".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username, "ADMIN");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", "ADMIN",
                "message", "Login successful"
            ));
        }

        // Regular member user
        if ("member".equals(username) && "member123".equals(password)) {
            String token = jwtUtil.generateToken(username, "MEMBER");
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username,
                "role", "MEMBER",
                "message", "Login successful"
            ));
        }

        return ResponseEntity.status(401).body(Map.of(
            "error", "Invalid credentials",
            "message", "Check your username and password"
        ));
    }
}
