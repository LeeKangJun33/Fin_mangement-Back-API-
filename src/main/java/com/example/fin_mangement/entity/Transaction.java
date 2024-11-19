package com.example.fin_mangement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID를 독립적으로 저장
    @Column(name = "username", nullable = false)
    private String username;

    // User 객체와의 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)  // userId와 중복 매핑 방지
    private User user;

    private String description;
    private double amount;
    private LocalDateTime date;
    private String category;

    // 필요 시 추가적인 생성자
    public Transaction(String username, String description, double amount, LocalDateTime date, String category) {
        this.username = username;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
}
