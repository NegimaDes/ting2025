package com.example.demo.service;

import com.example.demo.model.RateEntity;
import com.example.demo.repository.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    private final RateRepository rateRepository;

    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public RateEntity configureRates(RateEntity rates, String role) {
        if (!"ADMIN".equals(role)) {
            throw new SecurityException("Only administrators can configure rates.");
        }
        return rateRepository.save(rates);
    }

    public RateEntity getRates() {
        return rateRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Rates not configured yet."));
    }
}

