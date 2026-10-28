package com.foodredistribution.service;

import com.foodredistribution.model.DonationStatusLog;
import com.foodredistribution.repository.DonationStatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationStatusLogService {

    private final DonationStatusLogRepository logRepository;

    public List<DonationStatusLog> getAll() {
        return logRepository.findAll();
    }

    /** Full status history for a donation, oldest first — useful for a timeline view. */
    public List<DonationStatusLog> getHistoryForDonation(Long donationId) {
        return logRepository.findByDonation_DonationIdOrderByChangedAtAsc(donationId);
    }
}
