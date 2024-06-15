package com.example.FinancialCalculator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.FinancialCalculator.models.Calculation;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {
    List<Calculation> findByUserId(Long userId);
}
