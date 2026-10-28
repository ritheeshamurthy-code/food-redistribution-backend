package com.foodredistribution.service;

import com.foodredistribution.model.Admin;
import com.foodredistribution.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id " + id));
    }

    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin update(Long id, Admin updated) {
        Admin existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setAccessLevel(updated.getAccessLevel());
        return adminRepository.save(existing);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
}
