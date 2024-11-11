package com.example.fin_mangement.controller;

import com.example.fin_mangement.entity.User;
import com.example.fin_mangement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // 사용자 이름과 이메일이 중복되는지 확인
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "이미 사용 중인 사용자 이름입니다.";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "이미 사용 중인 이메일입니다.";
        }

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "회원가입이 완료되었습니다!";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }
}



