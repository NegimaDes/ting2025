package com.example.demo.service;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.LoanEntity;
import com.example.demo.model.ToolEntity;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.MovementRepository;
import com.example.demo.repository.ToolRepository;
import org.springframework.stereotype.Service;

import javax.tools.Tool;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private final ToolRepository toolRepository;

    public ReportService(LoanRepository loanRepository,
                         ClientRepository clientRepository,
                         ToolRepository toolRepository) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
        this.toolRepository = toolRepository;
    }

    // Active loans (valid or overdue)
    public List<LoanEntity> getActiveLoans() {
        return loanRepository.findByStatusIn(List.of("ACTIVE", "OVERDUE"));
    }

    // Clients with overdue loans
    public List<ClientEntity> getClientsWithOverdueLoans() {
        return clientRepository.findClientsWithOverdueLoans();
    }

    // Most borrowed tools (ranking)
    public List<ToolEntity> getMostBorrowedTools(LocalDate start, LocalDate end) {
        return toolRepository.findMostBorrowedTools(start, end);
    }

}
