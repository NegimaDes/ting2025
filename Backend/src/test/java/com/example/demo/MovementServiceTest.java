package com.example.demo;

import com.example.demo.model.MovementEntity;
import com.example.demo.model.MovementType;
import com.example.demo.model.ToolEntity;
import com.example.demo.repository.MovementRepository;
import com.example.demo.service.MovementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

    private final MovementRepository movementRepository = Mockito.mock(MovementRepository.class);
    private final MovementService movementService = new MovementService(movementRepository);

    @Test
    void registerMovement_shouldSaveMovement() {
        ToolEntity tool = ToolEntity.builder().id(1L).name("Hammer").build();

        Mockito.when(movementRepository.save(Mockito.any(MovementEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MovementEntity movement = movementService.registerMovement(tool, MovementType.LOAN, 1, "admin");

        assertEquals(MovementType.LOAN, movement.getType());
        assertEquals("admin", movement.getResponsibleUser());
    }
}
