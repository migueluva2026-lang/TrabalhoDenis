package com.example.TrabalhoDenis.controller;

import lombok.Data;

@Data
public class LoginRequest { // Isso se Chama DTO, é um Objeto, nesse caso aqui ele tem só o que é necessário pro login. Antes era User (Que tem muita coisa) agora passa só por isso aqui
    private String email;
    private String password;
}