package com.example.TrabalhoDenis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TrabalhoDenis.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}

