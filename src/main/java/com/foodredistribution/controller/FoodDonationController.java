package com.foodredistribution.controller;

import com.foodredistribution.model.DonationStatus;
import com.foodredistribution.model.FoodDonation;
import com.foodredistribution.model.User;
import com.foodredistribution.service.FoodDonationService;
import com.foodredistribution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class FoodDonationController {

    private final FoodDonationService foodDonationService;
    private final UserService userService;

    @GetMapping
    public List<FoodDonation> getAll(
            @RequestParam(required = false) Long donorId,
            @RequestParam(required = false) DonationStatus status) {
        if (donorId != null) return foodDonationService.getByDonor(donorId);
        if (status != null) return foodDonationService.getByStatus(status);
        return foodDonationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDonation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodDonationService.getById(id));
    }

    @PostMapping
    public ResponseEntity<FoodDonation> create(@RequestBody FoodDonation donation) {
        return ResponseEntity.ok(foodDonationService.create(donation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDonation> update(@PathVariable Long id, @RequestBody FoodDonation donation) {
        return ResponseEntity.ok(foodDonationService.update(id, donation));
    }

    /** Moves a donation through the workflow (posted -> screened -> matched -> ... ) and logs it. */
    @PatchMapping("/{id}/status")
    public ResponseEntity<FoodDonation> changeStatus(
            @PathVariable Long id,
            @RequestParam DonationStatus status,
            @RequestParam(required = false) Long changedByUserId) {
        User changedBy = changedByUserId != null ? userService.getById(changedByUserId) : null;
        return ResponseEntity.ok(foodDonationService.changeStatus(id, status, changedBy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodDonationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
