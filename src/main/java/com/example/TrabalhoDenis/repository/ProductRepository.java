package com.example.TrabalhoDenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TrabalhoDenis.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
