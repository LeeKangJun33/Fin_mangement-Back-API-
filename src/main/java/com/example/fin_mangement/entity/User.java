package com.example.fin_mangement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private boolean enabled;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}

