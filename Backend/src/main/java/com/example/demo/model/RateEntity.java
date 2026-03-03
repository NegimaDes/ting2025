package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Daily rental fee
    @Column(nullable = false)
    private double dailyRentalFee;

    // Daily fine for late returns
    @Column(nullable = false)
    private double dailyLateFee;

    // Default replacement value (can be overridden per tool)
    @Column(nullable = false)
    private double defaultReplacementValue;
}
