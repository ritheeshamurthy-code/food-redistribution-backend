package com.foodredistribution.repository;

import com.foodredistribution.model.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {
    Optional<Pickup> findByDonation_DonationId(Long donationId);
    List<Pickup> findByNgo_NgoId(Long ngoId);
}
