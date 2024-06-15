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
import com.example.FinancialCalculator.parameters.LumpsumParameters;
import com.example.FinancialCalculator.services.CalculationService;
import com.example.FinancialCalculator.services.UserService;

@Controller
@RequestMapping("/calculator")
public class LumpsumController {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private UserService userService;

    @ModelAttribute("lumpsumParams")
    public LumpsumParameters lumpsumParams() {
        return new LumpsumParameters();
    }

    @GetMapping("/lumpsum")
    public String showLumpsumForm(Model model) {
        return "lumpsum";
    }

    @PostMapping("/calculateLumpsum")
    public String calculateLumpsum(@ModelAttribute("lumpsumParams") LumpsumParameters lumpsumParams, Model model) {
        long principal = lumpsumParams.getPrincipal();
        double roi = lumpsumParams.getRoi();
        int time = lumpsumParams.getTime();
        int frequency = lumpsumParams.getFrequency();
        
        // Calculation logic
        double amount = principal * Math.pow((1 + roi / frequency), time * frequency);
        double earnings = amount - principal;

        model.addAttribute("amount", Math.round(amount));
        model.addAttribute("earnings", Math.round(earnings));
        model.addAttribute("params", lumpsumParams);
        model.addAttribute("principal", principal);

        saveCalculation("Lumpsum", lumpsumParams.toString(), Math.round(amount) + "");

        return "lumpsum";
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
