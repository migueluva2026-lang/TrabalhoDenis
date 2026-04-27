package com.example.TrabalhoDenis.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")


public class Category { // Diz oq essa entidade deve ter e como ela vai funcionar no db

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}