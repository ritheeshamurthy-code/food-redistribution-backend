package com.foodredistribution.repository;

import com.foodredistribution.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    Optional<Distribution> findByDonation_DonationId(Long donationId);
    List<Distribution> findByNgo_NgoId(Long ngoId);
}
