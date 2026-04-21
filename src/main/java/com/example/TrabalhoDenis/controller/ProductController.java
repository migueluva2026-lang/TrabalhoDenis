package com.example.TrabalhoDenis.controller;

import com.example.TrabalhoDenis.service.ProductService;
import com.example.TrabalhoDenis.model.Product;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}