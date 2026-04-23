package com.example.TrabalhoDenis.controller;

import com.example.TrabalhoDenis.model.User;
import com.example.TrabalhoDenis.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController { // Controller define um endpoint e as suas rotas. Nesse caso a rota é /api/users e seus possíveis "métodos" são Get, Post, Put e Delete.

    private final UserService userService; // Eu criei esse endpoint mas ele não vai ser muito explorado, provavelmente. O foco é Products

    @GetMapping
    public List<User> listAll() { // Ao chamar a API pelo /api/users/ com o header GET, vem pra essa função
        return userService.listAll(); // Essa função chama o service e sua função listAll, lá contem a lógica da API
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) { // Basicamente a mesma coisa só que quando se passa um ID ele chama essa ao invés da outra
        return userService.findById(id); // E ela chama uma função diferente, que é a de encontrar pelo ID específico
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) { // POST para salvar
        return ResponseEntity.status(201).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) { // PUT para sobreescrever/atualizar
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}