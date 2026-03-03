package com.example.demo.controller;

import com.example.demo.model.RateEntity;
import com.example.demo.service.RateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    public RateEntity configureRates(@RequestBody RateEntity rates, @RequestParam String role) {
        return rateService.configureRates(rates, role);
    }

    @GetMapping
    public RateEntity getRates() {
        return rateService.getRates();
    }
}
