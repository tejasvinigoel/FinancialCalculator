package com.example.FinancialCalculator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.FinancialCalculator.models.Calculation;
import com.example.FinancialCalculator.models.User;
import com.example.FinancialCalculator.parameters.LoanEMIParameters;
import com.example.FinancialCalculator.services.CalculationService;
import com.example.FinancialCalculator.services.UserService;

@Controller
@RequestMapping("/calculator")
public class LoanEMIController {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private UserService userService;

    @ModelAttribute("loanEMIParams")
    public LoanEMIParameters loanEMIParams() {
        return new LoanEMIParameters();
    }

    @GetMapping("/loan_emi")
    public String showLoanEMIForm(Model model) {
        return "loan_emi";
    }

    @PostMapping("/calculateEMI")
    public String calculateEMI(@ModelAttribute("loanEMIParams") LoanEMIParameters loanEMIParams, Model model) {
        double loanAmount = loanEMIParams.getLoanAmount();
        double annualInterestRate = loanEMIParams.getAnnualInterestRate() / 100;
        int loanTenure = loanEMIParams.getLoanTenure();

        // Calculation logic for EMI
        double monthlyInterestRate = annualInterestRate / 12;
        int numberOfPayments = loanTenure * 12;

        double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

        // Round off to two decimal places
        emi = Math.round(emi * 100.0) / 100.0;

        // Save calculation to database
        saveCalculation("EMI", loanEMIParams.toString(), emi + "");

        // Prepare model attributes for response
        model.addAttribute("loanAmount", loanAmount);
        model.addAttribute("annualInterestRate", annualInterestRate * 100);
        model.addAttribute("loanTenure", loanTenure);
        model.addAttribute("emi", emi);
        model.addAttribute("params", loanEMIParams);

        return "loan_emi_result";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = getCurrentUser();
        List<Calculation> calculations = calculationService.getCalculationsByUserId(user.getId());
        model.addAttribute("calculations", calculations);
        return "dashboard";
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUsername(username);
        }
        return null;
    }

    private void saveCalculation(String type, String parameters, String result) {
        User user = getCurrentUser();
        if (user != null) {
            Calculation calculation = new Calculation();
            calculation.setType(type);
            calculation.setParameters(parameters);
            calculation.setResult(result);
            calculation.setUserId(user.getId());
            calculationService.saveCalculation(calculation);
        }
    }
}
