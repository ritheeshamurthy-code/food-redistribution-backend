package com.foodredistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "donation_status_log")
@Getter
@Setter
@NoArgsConstructor
public class DonationStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private FoodDonation donation;

    private String oldStatus;

    private String newStatus;

    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @PrePersist
    protected void onCreate() {
        this.changedAt = LocalDateTime.now();
    }
}
