package com.example.FinancialCalculator.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinancialCalculator.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
