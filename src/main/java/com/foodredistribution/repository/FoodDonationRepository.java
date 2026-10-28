package com.foodredistribution.repository;

import com.foodredistribution.model.DonationStatus;
import com.foodredistribution.model.FoodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {
    List<FoodDonation> findByDonor_DonorId(Long donorId);
    List<FoodDonation> findByStatus(DonationStatus status);
}
