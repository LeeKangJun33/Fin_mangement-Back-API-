package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.User;
import com.example.fin_mangement.repository.UserRepository;
import com.example.fin_mangement.service.CustomUserDetailsService;
import com.example.fin_mangement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();

        try {
            // 사용자 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // 사용자 세부 정보 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // JWT 토큰 생성
            String token = jwtUtil.generateToken(userDetails);
            response.put("token", token);

        } catch (AuthenticationException e) {
            response.put("error", "Invalid username or password");
        }

        return response;
    }
}
