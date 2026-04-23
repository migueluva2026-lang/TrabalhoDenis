package com.example.TrabalhoDenis.service;

import com.example.TrabalhoDenis.model.Category;
import com.example.TrabalhoDenis.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category updated) {
        Category categoria = findById(id);
        categoria.setName(updated.getName());
        return categoryRepository.save(categoria);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}