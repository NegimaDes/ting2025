package com.example.demo.controller;

import com.example.demo.model.MovementEntity;
import com.example.demo.model.ToolEntity;
import com.example.demo.service.MovementService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public MovementEntity registerMovement(@RequestBody ToolEntity tool,
                                           @RequestParam String type,
                                           @RequestParam int quantity,
                                           @RequestParam String user) {
        return movementService.registerMovement(tool,
                Enum.valueOf(com.example.demo.model.MovementType.class, type),
                quantity,
                user);
    }

    @GetMapping("/tool/{id}")
    public List<MovementEntity> getMovementsByTool(@PathVariable ToolEntity id) {
        return movementService.getMovementsByTool(id);
    }

    @GetMapping("/range")
    public List<MovementEntity> getMovementsByDateRange(@RequestParam String start,
                                                        @RequestParam String end) {
        return movementService.getMovementsByDateRange(LocalDateTime.parse(start), LocalDateTime.parse(end));
    }
}

