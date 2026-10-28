package com.foodredistribution.service;

import com.foodredistribution.model.*;
import com.foodredistribution.repository.MatchingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final FoodDonationService foodDonationService;

    /** How long to wait after the FIRST accept before finalizing the winner. */
    private static final long BUFFER_WINDOW_SECONDS = 5;

    public List<Matching> getAll() {
        return matchingRepository.findAll();
    }

    public Matching getById(Long id) {
        return matchingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id " + id));
    }

    public List<Matching> getRankedMatchesForDonation(Long donationId) {
        return matchingRepository.findByDonation_DonationIdOrderByMatchScoreDesc(donationId);
    }

    public List<Matching> getByNgo(Long ngoId) {
        return matchingRepository.findByNgo_NgoId(ngoId);
    }

    public Matching create(Matching matching) {
        return matchingRepository.save(matching);
    }

    public void delete(Long id) {
        matchingRepository.deleteById(id);
    }

    /**
     * Called when an NGO clicks "Accept" on a donation.
     * Does NOT immediately hand over the donation — just records the click.
     * The scheduled finalizer below decides the actual winner shortly after.
     */
    @Transactional
    public Matching recordAccept(Long matchId) {
        Matching match = getById(matchId);

        if (match.getDonation().getStatus() != DonationStatus.MATCHED) {
            throw new IllegalStateException("This donation is no longer available.");
        }

        match.setNotificationStatus(NotificationStatus.ACCEPTED);
        match.setAcceptedAt(LocalDateTime.now());
        return matchingRepository.save(match);
    }

    public Matching recordDecline(Long matchId) {
        Matching match = getById(matchId);
        match.setNotificationStatus(NotificationStatus.DECLINED);
        return matchingRepository.save(match);
    }

    /**
     * Runs every 2 seconds. For every donation that has at least one ACCEPTED
     * click, waits until BUFFER_WINDOW_SECONDS has passed since the first
     * click, then picks the highest match_score among everyone who accepted
     * in that window — NOT whoever clicked first. Everyone else is expired.
     */
    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void finalizePendingMatches() {
        List<Long> donationIds = matchingRepository.findDonationIdsWithPendingAccepts();

        for (Long donationId : donationIds) {
            List<Matching> accepted = matchingRepository
                    .findByDonation_DonationIdAndNotificationStatus(donationId, NotificationStatus.ACCEPTED);

            if (accepted.isEmpty()) continue;

            LocalDateTime firstAcceptTime = accepted.stream()
                    .map(Matching::getAcceptedAt)
                    .min(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            boolean windowElapsed = ChronoUnit.SECONDS.between(firstAcceptTime, LocalDateTime.now())
                    >= BUFFER_WINDOW_SECONDS;

            if (!windowElapsed) continue; // still collecting responses, check again next cycle

            // Pick the best-ranked NGO among everyone who accepted in the window
            Matching winner = accepted.stream()
                    .max(Comparator.comparing(Matching::getMatchScore))
                    .orElseThrow();

            winner.setNotificationStatus(NotificationStatus.ACCEPTED);
            matchingRepository.save(winner);

            // Expire every other match for this donation (accepted, sent, or declined)
            List<Matching> allMatches = matchingRepository.findByDonation_DonationIdOrderByMatchScoreDesc(donationId);
            for (Matching m : allMatches) {
                if (!m.getMatchId().equals(winner.getMatchId())) {
                    m.setNotificationStatus(NotificationStatus.EXPIRED);
                    matchingRepository.save(m);
                }
            }

            // Finalize the donation itself
            foodDonationService.changeStatus(donationId, DonationStatus.ACCEPTED, null);
        }
    }
}
