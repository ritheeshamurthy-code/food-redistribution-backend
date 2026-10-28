package com.foodredistribution.repository;

import com.foodredistribution.model.Ngo;
import com.foodredistribution.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {
    Optional<Ngo> findByUser_UserId(Long userId);
    List<Ngo> findByVerificationStatus(VerificationStatus status);
}
