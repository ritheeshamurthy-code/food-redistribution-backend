package com.foodredistribution.repository;

import com.foodredistribution.model.EligibilityScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EligibilityScreeningRepository extends JpaRepository<EligibilityScreening, Long> {
    Optional<EligibilityScreening> findByDonation_DonationId(Long donationId);
}
