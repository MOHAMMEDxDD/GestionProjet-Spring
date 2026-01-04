package com.planification.gestionprojetweb.repository;

import com.planification.gestionprojetweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    // === Filter par Role (Bach njibo ghir les devs) ===
    List<User> findByRole(String role);
}