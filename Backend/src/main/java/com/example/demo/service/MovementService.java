package com.example.demo.service;

import com.example.demo.model.MovementEntity;
import com.example.demo.model.MovementType;
import com.example.demo.model.ToolEntity;
import com.example.demo.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementService {

    private final MovementRepository movementRepository;

    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public MovementEntity registerMovement(ToolEntity tool, MovementType type, int quantity, String user) {
        MovementEntity movement = MovementEntity.builder()
                .tool(tool)
                .type(type)
                .quantity(quantity)
                .timestamp(LocalDateTime.now())
                .responsibleUser(user)
                .build();
        return movementRepository.save(movement);
    }

    public List<MovementEntity> getMovementsByTool(ToolEntity tool) {
        return movementRepository.findByTool(tool);
    }

    public List<MovementEntity> getMovementsByDateRange(LocalDateTime start, LocalDateTime end) {
        return movementRepository.findByTimestampBetween(start, end);
    }
}
