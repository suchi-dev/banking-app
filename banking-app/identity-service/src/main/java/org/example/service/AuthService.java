package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.UserCredential;
import org.example.repository.UserCredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public String saveUser(UserCredential userCredentialRequest){
        userCredentialRequest.setPassword(passwordEncoder.encode(userCredentialRequest.getPassword()));
        userCredentialRepository.save(userCredentialRequest);
        return "user added to the system";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
