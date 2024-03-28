package com.example.shopappbackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fullname",length = 100)
    private String fullName;
    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;
    @Column(length = 200)
    private String address;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "facebook_account_id")
    private int facebookAccountId;
    @Column(name = "google_account_id")
    private int googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
