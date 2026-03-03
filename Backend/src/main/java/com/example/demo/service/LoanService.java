package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final ToolRepository toolRepository;
    private final RateService rateService;

    private static final int MAX_ACTIVE_LOANS = 5;

    public LoanService(LoanRepository loanRepository, ToolRepository toolRepository,  RateService rateService) {
        this.loanRepository = loanRepository;
        this.toolRepository = toolRepository;
        this.rateService = rateService;
    }

    public LoanEntity registerLoan(ClientEntity client, ToolEntity tool, LocalDate deliveryDate, LocalDate expectedReturnDate) {
        // Business rule validations
        if (client.getStatus() != ClientStatus.ACTIVE) {
            throw new IllegalStateException("Client is restricted.");
        }
        List<LoanEntity> activeLoans = loanRepository.findByClientAndStatus(client, LoanStatus.ACTIVE);
        if (activeLoans.size() >= MAX_ACTIVE_LOANS) {
            throw new IllegalStateException("Client has reached maximum active loans.");
        }
        if (tool.getStatus() != ToolStatus.AVAILABLE || tool.getStock() < 1) {
            throw new IllegalStateException("Tool is not available.");
        }
        if (expectedReturnDate.isBefore(deliveryDate)) {
            throw new IllegalArgumentException("Return date cannot be before delivery date.");
        }

        // Update tool stock
        tool.setStock(tool.getStock() - 1);
        if (tool.getStock() < 1) {
            tool.setStatus(ToolStatus.LOANED);
        }
        toolRepository.save(tool);

        // Save loan
        LoanEntity loan = LoanEntity.builder()
                .client(client)
                .tool(tool)
                .deliveryDate(deliveryDate)
                .expectedReturnDate(expectedReturnDate)
                .status(LoanStatus.ACTIVE)
                .build();

        return loanRepository.save(loan);
    }

    public LoanEntity registerReturn(Long loanId, LocalDate actualReturnDate, boolean damaged) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        loan.setActualReturnDate(actualReturnDate);
        RateEntity rates = rateService.getRates();

        // Fine calculation
        if (actualReturnDate.isAfter(loan.getExpectedReturnDate())) {
            long daysLate = ChronoUnit.DAYS.between(loan.getExpectedReturnDate(), actualReturnDate);
            loan.setFine(daysLate * rates.getDailyLateFee());
            loan.setStatus(LoanStatus.OVERDUE);
        } else {
            loan.setStatus(LoanStatus.RETURNED);
        }

        // Tool state update
        ToolEntity tool = loan.getTool();
        if (damaged) {
            tool.setStatus(ToolStatus.UNDER_REPAIR);
            loan.setFine(loan.getFine() + replacementValue(tool));
        } else {
            tool.setStock(tool.getStock() + 1);
            tool.setStatus(ToolStatus.AVAILABLE);
        }
        toolRepository.save(tool);

        return loanRepository.save(loan);
    }

    public double replacementValue(ToolEntity tool){
        if (tool.getReplacementValue() <= 0) {
            return rateService.getRates().getDefaultReplacementValue();
        }
        return tool.getReplacementValue();
    }
}
