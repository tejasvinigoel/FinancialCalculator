package com.example.FinancialCalculator.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinancialCalculator.models.Calculation;
import com.example.FinancialCalculator.repositories.CalculationRepository;
@Service
public class CalculationService {

    @Autowired
    private CalculationRepository calculationRepository;

    public void saveCalculation(Calculation calculation) {
        calculationRepository.save(calculation);
    }

    public List<Calculation> getCalculationsByUserId(Long userId) {
        return calculationRepository.findByUserId(userId);
    }
}
