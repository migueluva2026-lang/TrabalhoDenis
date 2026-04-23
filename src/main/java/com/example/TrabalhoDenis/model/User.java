package com.example.TrabalhoDenis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")


public class User { // Tabela de informações de um Usuário no Banco de Dados. Aqui diz o que um usuário tem que ter (e se pode ficar vazio)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore // Tinha esquecido, isso faz com que não retorne a senha na resposta da API. Não sei fazer criptografia então vai ficar assim
    private String password;

    @Column(nullable = false)
    private String role = "ADMIN";

    @Column(nullable = false)
    private Boolean active = true;
}