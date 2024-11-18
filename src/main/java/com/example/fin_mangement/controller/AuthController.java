package com.example.fin_mangement.controller;

import com.example.fin_mangement.dto.AuthRequest;
import com.example.fin_mangement.dto.AuthResponse;
import com.example.fin_mangement.dto.UserDto;
import com.example.fin_mangement.entity.User;
import com.example.fin_mangement.repository.UserRepository;
import com.example.fin_mangement.service.CustomUserDetailsService;
import com.example.fin_mangement.service.UserService;
import com.example.fin_mangement.util.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final UserService userService;

    // 로그인 요청 처리 및 JWT 토큰 발급
    @PostMapping("/register")
    public ResponseEntity<Map<String,String >>registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());

            // 성공 응답
            Map<String, String> response = new HashMap<>();
            response.put("message", "회원가입에 성공했습니다!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 실패 응답
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponse(jwt));
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new AuthResponse("잘못된 증명입니다."));
        }
    }
}