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
import com.example.FinancialCalculator.parameters.NAPSParameters;
import com.example.FinancialCalculator.services.CalculationService;
import com.example.FinancialCalculator.services.UserService;

@Controller
@RequestMapping("/calculator")
public class NAPSController {

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private UserService userService;

    @ModelAttribute("napsParams")
    public NAPSParameters napsParams() {
        return new NAPSParameters();
    }

    @GetMapping("/naps")
    public String showNAPSForm(Model model) {
        return "naps";
    }

    @PostMapping("/calculateNAPS")
    @ResponseBody
    public double calculateNAPS(@ModelAttribute("napsParams") NAPSParameters napsParams) {
        double totalAssets = napsParams.getTotalAssets();
        double totalLiabilities = napsParams.getTotalLiabilities();
        int numberOfShares = napsParams.getNumberOfShares();
        double naps = (totalAssets - totalLiabilities) / numberOfShares;

        saveCalculation("NAPS", napsParams.toString(), naps + "");

        return Math.round(naps);
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
