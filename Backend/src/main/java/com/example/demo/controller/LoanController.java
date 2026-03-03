package com.example.demo.controller;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.LoanEntity;
import com.example.demo.model.ToolEntity;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final ClientRepository clientRepository;
    private final ToolRepository toolRepository;


    public LoanController(LoanService loanService, ClientRepository clientRepository, ToolRepository toolRepository) {
        this.loanService = loanService;
        this.clientRepository = clientRepository;
        this.toolRepository = toolRepository;
    }

    @PostMapping
    public LoanEntity registerLoan(@RequestParam Long clientid,
                                   @RequestParam Long toolId,
                                   @RequestParam String deliveryDate,
                                   @RequestParam String expectedReturnDate) {
        ClientEntity client = clientRepository.findById(clientid)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        ToolEntity tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new IllegalArgumentException("Tool not found"));
        return loanService.registerLoan(client, tool,
                LocalDate.parse(deliveryDate),
                LocalDate.parse(expectedReturnDate));
    }

    @PutMapping("/{id}/return")
    public LoanEntity registerReturn(@PathVariable Long id,
                                     @RequestParam String actualReturnDate,
                                     @RequestParam boolean damaged) {
        return loanService.registerReturn(id, LocalDate.parse(actualReturnDate), damaged);
    }

}
