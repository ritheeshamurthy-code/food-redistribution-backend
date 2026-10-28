package com.foodredistribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_donations")
@Getter
@Setter
@NoArgsConstructor
public class FoodDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String foodName;

    @NotNull
    private Integer quantity;

    private String quantityUnit = "meals";

    private LocalDateTime preparedAt;

    private LocalDateTime useByTime;

    private String storageCondition;

    private String packagingCondition;

    private String allergenInfo;

    private String pickupAddress;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private DonationStatus status = DonationStatus.POSTED;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
