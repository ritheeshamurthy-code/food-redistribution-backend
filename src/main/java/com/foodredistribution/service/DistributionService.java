package com.foodredistribution.service;

import com.foodredistribution.model.Distribution;
import com.foodredistribution.repository.DistributionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributionService {

    private final DistributionRepository distributionRepository;

    public List<Distribution> getAll() {
        return distributionRepository.findAll();
    }

    public Distribution getById(Long id) {
        return distributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Distribution not found with id " + id));
    }

    public List<Distribution> getByNgo(Long ngoId) {
        return distributionRepository.findByNgo_NgoId(ngoId);
    }

    public Distribution create(Distribution distribution) {
        return distributionRepository.save(distribution);
    }

    public void delete(Long id) {
        distributionRepository.deleteById(id);
    }
}
