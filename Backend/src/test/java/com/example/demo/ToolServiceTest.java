package com.example.demo;

import com.example.demo.model.ToolEntity;
import com.example.demo.model.ToolStatus;
import com.example.demo.repository.ToolRepository;
import com.example.demo.service.ToolService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ToolServiceTest {

    private final ToolRepository toolRepository = Mockito.mock(ToolRepository.class);
    private final ToolService toolService = new ToolService(toolRepository);

    @Test
    void registerTool_withValidData_shouldSetStatusAvailable() {
        ToolEntity tool = ToolEntity.builder()
                .name("Hammer")
                .category("Hand Tools")
                .replacementValue(50.0)
                .stock(10)
                .build();

        Mockito.when(toolRepository.save(Mockito.any(ToolEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ToolEntity saved = toolService.registerTool(tool);

        assertEquals(ToolStatus.AVAILABLE, saved.getStatus());
    }

    @Test
    void deactivateTool_withNonAdminRole_shouldThrowException() {
        ToolEntity tool = ToolEntity.builder()
                .id(1L)
                .name("Hammer")
                .status(ToolStatus.AVAILABLE)
                .build();

        Mockito.when(toolRepository.findById(1L)).thenReturn(Optional.of(tool));

        assertThrows(SecurityException.class,
                () -> toolService.deactivateTool(1L, "EMPLOYEE"));
    }
}
