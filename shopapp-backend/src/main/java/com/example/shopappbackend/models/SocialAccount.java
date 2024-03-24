package com.example.shopappbackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_account")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20)
    private String provider;
    @Column(name = "provider_id", length = 50)
    private String providerId;
    @Column(length = 150)
    private String email;
    @Column(length = 100)
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
