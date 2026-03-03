package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.RateRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.service.LoanService;
import com.example.demo.service.RateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    private final LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
    private final ToolRepository toolRepository = Mockito.mock(ToolRepository.class);
    private final RateService rateService = Mockito.mock(RateService.class);
    private final LoanService loanService = new LoanService(loanRepository, toolRepository, rateService);

    @Test
    void registerLoan_withValidClientAndTool_shouldCreateLoan() {
        ClientEntity client = ClientEntity.builder().status(ClientStatus.ACTIVE).build();
        ToolEntity tool = ToolEntity.builder().status(ToolStatus.AVAILABLE).stock(2).build();

        Mockito.when(loanRepository.findByClientAndStatus(client, LoanStatus.ACTIVE))
                .thenReturn(List.of());
        Mockito.when(toolRepository.save(Mockito.any(ToolEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(loanRepository.save(Mockito.any(LoanEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanEntity loan = loanService.registerLoan(client, tool,
                LocalDate.now(), LocalDate.now().plusDays(3));

        assertEquals(LoanStatus.ACTIVE, loan.getStatus());
        assertEquals(1, tool.getStock());
    }

    @Test
    void registerReturn_withLateReturn_shouldCalculateFine() {
        ToolEntity tool = ToolEntity.builder().status(ToolStatus.LOANED).stock(0).build();
        LoanEntity loan = LoanEntity.builder()
                .id(1L)
                .tool(tool)
                .expectedReturnDate(LocalDate.now().minusDays(2))
                .status(LoanStatus.ACTIVE)
                .build();

        Mockito.when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        Mockito.when(toolRepository.save(Mockito.any(ToolEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(loanRepository.save(Mockito.any(LoanEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanEntity returned = loanService.registerReturn(1L, LocalDate.now(), false);

        assertTrue(returned.getFine() > 0);
        assertEquals(LoanStatus.OVERDUE, returned.getStatus());
    }
}
