//
// Código Feito por: Policarpo
//
package com.example.TrabalhoDenis.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitário para geração e validação de tokens JWT.
 * 
 * JWT (JSON Web Token) é um padrão para transmitir informações entre partes
 * como um objeto JSON compacto e autocontido.
 * 
 * Estrutura do token: HEADER.PAYLOAD.SIGNATURE
 *   - Header: algoritmo de assinatura (HS256)
 *   - Payload: dados do usuário (claims): email, role, expiração
 *   - Signature: garante que o token não foi alterado
 */
@Component
public class JwtUtil {

    /** Segredo lido do application.properties para assinar os tokens */
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /** Tempo de expiração em milissegundos (ex: 86400000 = 24h) */
    @Value("${app.jwt.expiration}")
    private Long jwtExpiration;

    /**
     * Gera um token JWT para o usuário autenticado.
     * 
     * @param userDetails dados do usuário do Spring Security
     * @return token JWT como String
     */
    public String gerarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Adiciona o papel (role) como claim extra no payload do token
        claims.put("roles", userDetails.getAuthorities().toString());
        return criarToken(claims, userDetails.getUsername());
    }

    /**
     * Monta o token com header, payload e assinatura.
     */
    private String criarToken(Map<String, Object> claims, String subject) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)           // Email do usuário no campo 'sub'
                .setIssuedAt(agora)            // Data de emissão
                .setExpiration(expiracao)      // Data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinatura HMAC-SHA256
                .compact();
    }

    /**
     * Converte o segredo String em uma chave criptográfica segura.
     */
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrai o email (subject) do token.
     * @param token token JWT
     * @return email do usuário
     */
    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    /**
     * Valida o token: verifica assinatura e expiração.
     * @param token token JWT
     * @param userDetails dados do usuário para comparar
     * @return true se o token é válido
     */
    public boolean validarToken(String token, UserDetails userDetails) {
        String email = extrairEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    /**
     * Verifica se o token já expirou.
     */
    private boolean isTokenExpirado(String token) {
        return extrairClaims(token).getExpiration().before(new Date());
    }

    /**
     * Faz o parsing do token e retorna os claims (payload).
     * Lança JwtException se o token for inválido ou expirado.
     */
    private Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
