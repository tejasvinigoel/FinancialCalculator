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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.FinancialCalculator.models.Calculation;
import com.example.FinancialCalculator.models.User;
import com.example.FinancialCalculator.parameters.SIPParameters;
import com.example.FinancialCalculator.services.CalculationService;
import com.example.FinancialCalculator.services.UserService;

@Controller
@RequestMapping("/calculator")
public class SIPController {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private UserService userService;

    @ModelAttribute("sipParams")
    public SIPParameters sipParams() {
        return new SIPParameters();
    }

    @GetMapping("/sip")
    public String showSIPForm(Model model) {
        return "sip";
    }

    @PostMapping("/calculateSIP")
    @ResponseBody
    public double calculateSIP(@ModelAttribute("sipParams") SIPParameters sipParams) {
        double monthlyInvestment = sipParams.getMonthlyInvestment();
        double annualInterestRate = sipParams.getAnnualInterestRate() / 100;
        int durationInYears = sipParams.getDurationInYears();
        double monthlyRate = annualInterestRate / 12;
        int months = durationInYears * 12;

        double futureValue = monthlyInvestment * ((Math.pow(1 + monthlyRate, months) - 1) / monthlyRate) * (1 + monthlyRate);

        saveCalculation("SIP", sipParams.toString(), futureValue + "");

        return Math.round(futureValue);
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
