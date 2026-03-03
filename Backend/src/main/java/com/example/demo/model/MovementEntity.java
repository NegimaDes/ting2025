package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tool affected
    @ManyToOne(optional = false)
    private ToolEntity tool;

    // Type of movement
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    // Quantity affected
    @Column(nullable = false)
    private int quantity;

    // Date and time of movement
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // User responsible
    @Column(nullable = false)
    private String responsibleUser;
}
