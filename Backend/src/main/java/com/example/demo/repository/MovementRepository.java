package com.example.demo.repository;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.MovementEntity;
import com.example.demo.model.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByTool(ToolEntity tool);
    List<MovementEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
