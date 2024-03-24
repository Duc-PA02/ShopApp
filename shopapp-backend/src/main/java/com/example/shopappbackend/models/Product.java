package com.example.shopappbackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 350)
    private String name;
    private Float price;
    @Column(length = 350)
    private String thumbnail;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
