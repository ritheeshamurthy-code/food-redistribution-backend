package com.foodredistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "distribution")
@Getter
@Setter
@NoArgsConstructor
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distributionId;

    @OneToOne
    @JoinColumn(name = "donation_id", nullable = false, unique = true)
    private FoodDonation donation;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private Ngo ngo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationResult verificationResult;

    private Integer peopleServed;

    private LocalDateTime distributedAt;

    private String notes;
}
