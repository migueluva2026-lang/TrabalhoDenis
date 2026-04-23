package com.example.TrabalhoDenis.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT — intercepta TODAS as requisições HTTP para validar o token.
 * 
 * Fluxo de execução:
 *   1. Extrai o header "Authorization: Bearer <token>"
 *   2. Valida o token com JwtUtil
 *   3. Carrega o usuário do banco de dados
 *   4. Registra a autenticação no SecurityContext do Spring
 * 
 * Estende OncePerRequestFilter: garante que o filtro é executado apenas uma vez por requisição.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Lê o header "Authorization" da requisição
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 2. Verifica se o header existe e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Remove o prefixo "Bearer " para ficar apenas com o token
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extrairEmail(token);
            } catch (Exception e) {
                // Token inválido/expirado: continua sem autenticar (retorna 401 mais adiante)
                logger.warn("Token JWT inválido: " + e.getMessage());
            }
        }

        // 3. Se temos o email e ainda não há autenticação no contexto atual
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 4. Valida o token contra os dados do usuário
            if (jwtUtil.validarToken(token, userDetails)) {
                // 5. Cria o objeto de autenticação do Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                          // credentials (não necessárias após autenticação)
                                userDetails.getAuthorities()   // roles do usuário
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. Registra a autenticação no SecurityContext (válido para a thread atual)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Passa para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }
}
