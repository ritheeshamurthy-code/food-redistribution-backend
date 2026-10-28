package com.foodredistribution.service;

import com.foodredistribution.model.Pickup;
import com.foodredistribution.model.PickupStatus;
import com.foodredistribution.repository.PickupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;

    public List<Pickup> getAll() {
        return pickupRepository.findAll();
    }

    public Pickup getById(Long id) {
        return pickupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pickup not found with id " + id));
    }

    public List<Pickup> getByNgo(Long ngoId) {
        return pickupRepository.findByNgo_NgoId(ngoId);
    }

    public Pickup create(Pickup pickup) {
        return pickupRepository.save(pickup);
    }

    public Pickup updateStatus(Long id, PickupStatus status) {
        Pickup existing = getById(id);
        existing.setStatus(status);
        if (status == PickupStatus.COMPLETED) {
            existing.setActualPickupTime(LocalDateTime.now());
        }
        return pickupRepository.save(existing);
    }

    public void delete(Long id) {
        pickupRepository.deleteById(id);
    }
}
