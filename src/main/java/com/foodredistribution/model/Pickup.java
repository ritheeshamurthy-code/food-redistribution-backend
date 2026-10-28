package com.foodredistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pickups")
@Getter
@Setter
@NoArgsConstructor
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickupId;

    @OneToOne
    @JoinColumn(name = "donation_id", nullable = false, unique = true)
    private FoodDonation donation;

    @ManyToOne
    @JoinColumn(name = "ngo_id", nullable = false)
    private Ngo ngo;

    private LocalDateTime scheduledTime;

    private LocalDateTime actualPickupTime;

    private Double distanceTravelledKm;

    @Enumerated(EnumType.STRING)
    private PickupStatus status = PickupStatus.PENDING;
}
