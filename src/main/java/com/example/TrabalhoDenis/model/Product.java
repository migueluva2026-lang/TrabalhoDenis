package com.example.TrabalhoDenis.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // executado automaticamente antes de qualquer INSERT
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(); // usa a biblioteca de tempo do java pra pegar a hora que foi criado. Acho que não chegamos a implementar isso no frontend? Talvez um TODO
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}