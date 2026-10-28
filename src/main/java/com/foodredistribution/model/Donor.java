package com.foodredistribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "donors")
@Getter
@Setter
@NoArgsConstructor
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String orgName;

    @Enumerated(EnumType.STRING)
    private DonorType donorType;

    private String phone;

    private String address;

    private Double latitude;

    private Double longitude;
}
