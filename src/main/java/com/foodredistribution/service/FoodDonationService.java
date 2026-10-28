package com.foodredistribution.service;

import com.foodredistribution.model.DonationStatus;
import com.foodredistribution.model.DonationStatusLog;
import com.foodredistribution.model.FoodDonation;
import com.foodredistribution.model.User;
import com.foodredistribution.repository.DonationStatusLogRepository;
import com.foodredistribution.repository.FoodDonationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodDonationService {

    private final FoodDonationRepository foodDonationRepository;
    private final DonationStatusLogRepository statusLogRepository;

    public List<FoodDonation> getAll() {
        return foodDonationRepository.findAll();
    }

    public List<FoodDonation> getByDonor(Long donorId) {
        return foodDonationRepository.findByDonor_DonorId(donorId);
    }

    public List<FoodDonation> getByStatus(DonationStatus status) {
        return foodDonationRepository.findByStatus(status);
    }

    public FoodDonation getById(Long id) {
        return foodDonationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation not found with id " + id));
    }

    public FoodDonation create(FoodDonation donation) {
        return foodDonationRepository.save(donation);
    }

    public FoodDonation update(Long id, FoodDonation updated) {
        FoodDonation existing = getById(id);
        existing.setFoodName(updated.getFoodName());
        existing.setQuantity(updated.getQuantity());
        existing.setQuantityUnit(updated.getQuantityUnit());
        existing.setPreparedAt(updated.getPreparedAt());
        existing.setUseByTime(updated.getUseByTime());
        existing.setStorageCondition(updated.getStorageCondition());
        existing.setPackagingCondition(updated.getPackagingCondition());
        existing.setAllergenInfo(updated.getAllergenInfo());
        existing.setPickupAddress(updated.getPickupAddress());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());
        return foodDonationRepository.save(existing);
    }

    /**
     * Changes a donation's status and automatically writes an audit
     * entry to DonationStatusLog, matching the workflow's tracking requirement.
     */
    public FoodDonation changeStatus(Long id, DonationStatus newStatus, User changedBy) {
        FoodDonation existing = getById(id);
        DonationStatus oldStatus = existing.getStatus();

        existing.setStatus(newStatus);
        FoodDonation saved = foodDonationRepository.save(existing);

        DonationStatusLog log = new DonationStatusLog();
        log.setDonation(saved);
        log.setOldStatus(oldStatus != null ? oldStatus.name() : null);
        log.setNewStatus(newStatus.name());
        log.setChangedBy(changedBy);
        statusLogRepository.save(log);

        return saved;
    }

    public void delete(Long id) {
        foodDonationRepository.deleteById(id);
    }
}
