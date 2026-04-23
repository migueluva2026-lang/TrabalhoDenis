package com.example.TrabalhoDenis.repository;
import com.example.TrabalhoDenis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // Um repository é a interface do banco de dados, ela "conversa" com ele
    Optional<User> findByEmail(String email);
}