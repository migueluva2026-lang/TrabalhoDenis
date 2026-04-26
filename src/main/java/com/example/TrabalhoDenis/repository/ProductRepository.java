package com.example.TrabalhoDenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TrabalhoDenis.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { // Um repository é uma interface do banco de dados, ela "conversa" com ele

}
