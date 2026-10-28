
package com.foodredistribution.service;

import com.foodredistribution.model.Donor;
import com.foodredistribution.model.User;
import com.foodredistribution.repository.DonorRepository;
import com.foodredistribution.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository;

    public List<Donor> getAll() {
        return donorRepository.findAll();
    }

    public Donor getById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Donor not found with id " + id));
    }

    @Transactional
    public Donor create(Donor donor) {

        // Validate the incoming User reference
        if (donor.getUser() == null ||
                donor.getUser().getUserId() == null) {
            throw new IllegalArgumentException(
                    "An existing userId must be provided");
        }

        Long userId = donor.getUser().getUserId();

        // Fetch the EXISTING user from the database
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with id " + userId));

        // Associate the existing user with the new donor
        donor.setUser(existingUser);

        // Save the donor, not a new user
        return donorRepository.save(donor);
    }

    @Transactional
    public Donor update(Long id, Donor updated) {

        // Retrieve the existing donor
        Donor existing = getById(id);

        // Update donor details only
        existing.setOrgName(updated.getOrgName());
        existing.setDonorType(updated.getDonorType());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());

        // Preserve the existing User association.
        // Do not replace or create a User during this update.
        return donorRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {

        // Ensure the donor exists before deleting
        Donor existing = getById(id);

        donorRepository.delete(existing);
    }
}
