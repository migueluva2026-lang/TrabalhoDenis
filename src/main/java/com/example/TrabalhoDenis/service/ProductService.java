package com.example.TrabalhoDenis.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.example.TrabalhoDenis.model.Product;
import com.example.TrabalhoDenis.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product save(Product product) {
        if (product.getPrice() <= 0) {
            throw new RuntimeException("Preço menor que 0");
        }
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}