package com.example.demo;

import com.example.demo.model.RateEntity;
import com.example.demo.repository.RateRepository;
import com.example.demo.service.RateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RateServiceTest {

    private final RateRepository rateRepository = Mockito.mock(RateRepository.class);
    private final RateService rateService = new RateService(rateRepository);

    @Test
    void configureRates_withAdminRole_shouldSaveRates() {
        RateEntity rates = RateEntity.builder()
                .dailyRentalFee(20.0)
                .dailyLateFee(10.0)
                .defaultReplacementValue(100.0)
                .build();

        Mockito.when(rateRepository.save(Mockito.any(RateEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RateEntity saved = rateService.configureRates(rates, "ADMIN");

        assertEquals(20.0, saved.getDailyRentalFee());
    }

    @Test
    void configureRates_withNonAdminRole_shouldThrowException() {
        RateEntity rates = RateEntity.builder().build();
        assertThrows(SecurityException.class,
                () -> rateService.configureRates(rates, "EMPLOYEE"));
    }
}
