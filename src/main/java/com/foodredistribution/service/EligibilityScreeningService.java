package com.foodredistribution.service;

import com.foodredistribution.model.EligibilityScreening;
import com.foodredistribution.repository.EligibilityScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EligibilityScreeningService {

    private final EligibilityScreeningRepository screeningRepository;

    public List<EligibilityScreening> getAll() {
        return screeningRepository.findAll();
    }

    public EligibilityScreening getById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found with id " + id));
    }

    public EligibilityScreening getByDonation(Long donationId) {
        return screeningRepository.findByDonation_DonationId(donationId)
                .orElseThrow(() -> new EntityNotFoundException("No screening for donation " + donationId));
    }

    public EligibilityScreening create(EligibilityScreening screening) {
        return screeningRepository.save(screening);
    }

    public void delete(Long id) {
        screeningRepository.deleteById(id);
    }
}
