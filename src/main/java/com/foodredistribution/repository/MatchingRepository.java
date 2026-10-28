package com.foodredistribution.repository;

import com.foodredistribution.model.Matching;
import com.foodredistribution.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findByDonation_DonationIdOrderByMatchScoreDesc(Long donationId);
    List<Matching> findByNgo_NgoId(Long ngoId);

    List<Matching> findByDonation_DonationIdAndNotificationStatus(Long donationId, NotificationStatus status);

    // Donations that have at least one ACCEPTED click still waiting to be finalized
    @org.springframework.data.jpa.repository.Query(
        "SELECT DISTINCT m.donation.donationId FROM Matching m WHERE m.notificationStatus = 'ACCEPTED'")
    List<Long> findDonationIdsWithPendingAccepts();
}
