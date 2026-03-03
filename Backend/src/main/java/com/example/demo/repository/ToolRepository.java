package com.example.demo.repository;

import com.example.demo.model.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface ToolRepository extends JpaRepository<ToolEntity, Long> {

    ToolEntity findByName(String name);

    @Query("SELECT l.tool FROM LoanEntity l WHERE l.deliveryDate BETWEEN :start AND :end " +
            "GROUP BY l.tool ORDER BY COUNT(l.tool) DESC")
    List<ToolEntity> findMostBorrowedTools(LocalDate start, LocalDate end);
}
