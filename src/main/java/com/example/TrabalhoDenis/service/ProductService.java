package com.example.TrabalhoDenis.service;

import com.example.TrabalhoDenis.model.Product;
import com.example.TrabalhoDenis.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product updated) {
        Product produto = findById(id);
        produto.setName(updated.getName());
        produto.setDescription(updated.getDescription());
        produto.setPrice(updated.getPrice());
        produto.setStockQuantity(updated.getStockQuantity());
        produto.setCategory(updated.getCategory());
        return productRepository.save(produto);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}