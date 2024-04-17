package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.UserCredential;
import org.example.service.AuthService;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@EnableDiscoveryClient
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String addNewUSer(@RequestBody UserCredential user){
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserCredential user) {
        return authService.generateToken(user.getName());
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
