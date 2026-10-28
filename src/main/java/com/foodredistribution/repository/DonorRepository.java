package com.foodredistribution.repository;

import com.foodredistribution.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByUser_UserId(Long userId);
}
