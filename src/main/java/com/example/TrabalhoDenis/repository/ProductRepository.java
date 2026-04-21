package com.example.TrabalhoDenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TrabalhoDenis.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
