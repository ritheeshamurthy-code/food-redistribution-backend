package com.foodredistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "EligibilityScreening")
@Getter
@Setter
@NoArgsConstructor
public class EligibilityScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screeningId;

    @OneToOne
    @JoinColumn(name = "donation_id", nullable = false, unique = true)
    private FoodDonation donation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScreeningResult result;

    private String reason;

    private LocalDateTime screenedAt;

    @PrePersist
    protected void onCreate() {
        this.screenedAt = LocalDateTime.now();
    }
}
