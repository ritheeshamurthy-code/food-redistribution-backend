package com.foodredistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "matching")
@Getter
@Setter
@NoArgsConstructor
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private FoodDonation donation;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private Ngo ngo;

    private Double distanceKm;

    private Double urgencyScore;

    private Double compatibilityScore;

    private Double matchScore;

    private LocalDateTime generatedAt;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus = NotificationStatus.SENT;

    private LocalDateTime acceptedAt;

    @PrePersist
    protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
}
