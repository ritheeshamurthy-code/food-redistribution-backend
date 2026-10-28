package com.foodredistribution.repository;

import com.foodredistribution.model.DonationStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationStatusLogRepository extends JpaRepository<DonationStatusLog, Long> {
    List<DonationStatusLog> findByDonation_DonationIdOrderByChangedAtAsc(Long donationId);
}
