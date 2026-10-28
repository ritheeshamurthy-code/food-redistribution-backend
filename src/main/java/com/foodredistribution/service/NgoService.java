package com.foodredistribution.service;

import com.foodredistribution.model.Ngo;
import com.foodredistribution.model.VerificationStatus;
import com.foodredistribution.repository.NgoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NgoService {

    private final NgoRepository ngoRepository;

    public List<Ngo> getAll() {
        return ngoRepository.findAll();
    }

    public List<Ngo> getByVerificationStatus(VerificationStatus status) {
        return ngoRepository.findByVerificationStatus(status);
    }

    public Ngo getById(Long id) {
        return ngoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NGO not found with id " + id));
    }

    public Ngo create(Ngo ngo) {
        return ngoRepository.save(ngo);
    }

    public Ngo update(Long id, Ngo updated) {
        Ngo existing = getById(id);
        existing.setNgoName(updated.getNgoName());
        existing.setRegistrationNo(updated.getRegistrationNo());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());
        existing.setFoodRequirement(updated.getFoodRequirement());
        existing.setCapacity(updated.getCapacity());
        return ngoRepository.save(existing);
    }

    public Ngo verify(Long id, VerificationStatus status) {
        Ngo existing = getById(id);
        existing.setVerificationStatus(status);
        return ngoRepository.save(existing);
    }

    public void delete(Long id) {
        ngoRepository.deleteById(id);
    }
}
