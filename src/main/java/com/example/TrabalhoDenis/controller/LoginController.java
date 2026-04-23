package com.example.TrabalhoDenis.controller;

import com.example.TrabalhoDenis.model.User;
import com.example.TrabalhoDenis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User request) {
        return userService.login(request.getEmail(), request.getPassword());
    }
}