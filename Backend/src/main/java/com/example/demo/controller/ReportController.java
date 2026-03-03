package com.example.demo.controller;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.LoanEntity;
import com.example.demo.model.ToolEntity;
import com.example.demo.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/loans/active")
    public List<LoanEntity> getActiveLoans() {
        return reportService.getActiveLoans();
    }

    @GetMapping("/clients/overdue")
    public List<ClientEntity> getClientsWithOverdueLoans() {
        return reportService.getClientsWithOverdueLoans();
    }

    @GetMapping("/tools/most-borrowed")
    public List<ToolEntity> getMostBorrowedTools(@RequestParam String start,
                                                 @RequestParam String end) {
        return reportService.getMostBorrowedTools(LocalDate.parse(start), LocalDate.parse(end));
    }
}
