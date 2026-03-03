package com.example.demo;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.LoanEntity;
import com.example.demo.model.LoanStatus;
import com.example.demo.model.ToolEntity;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    private final LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ToolRepository toolRepository = Mockito.mock(ToolRepository.class);
    private final ReportService reportService = new ReportService(loanRepository, clientRepository, toolRepository);

    @Test
    void getActiveLoans_shouldReturnLoans() {
        LoanEntity loan = LoanEntity.builder().status(LoanStatus.valueOf("ACTIVE")).build();
        Mockito.when(loanRepository.findByStatusIn(Mockito.<List<LoanStatus>>any()))
                .thenReturn(List.of(loan));

        List<LoanEntity> loans = reportService.getActiveLoans();
        assertEquals(1, loans.size());
    }

    @Test
    void getClientsWithOverdueLoans_shouldReturnClients() {
        ClientEntity client = ClientEntity.builder().name("John").build();
        Mockito.when(clientRepository.findClientsWithOverdueLoans())
                .thenReturn(List.of(client));

        List<ClientEntity> clients = reportService.getClientsWithOverdueLoans();
        assertEquals("John", clients.get(0).getName());
    }

    @Test
    void getMostBorrowedTools_shouldReturnTools() {
        ToolEntity tool = ToolEntity.builder().name("Drill").build();
        Mockito.when(toolRepository.findMostBorrowedTools(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(tool));

        List<ToolEntity> tools = reportService.getMostBorrowedTools(LocalDate.now().minusDays(10), LocalDate.now());
        assertEquals("Drill", tools.get(0).getName());
    }
}
