package com.foodredistribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ngos")
@Getter
@Setter
@NoArgsConstructor
public class Ngo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ngoId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String ngoName;

    private String registrationNo;

    private String phone;

    private String address;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private String foodRequirement;

    private Integer capacity;
}
