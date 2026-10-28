package com.foodredistribution.controller;

import com.foodredistribution.model.Pickup;
import com.foodredistribution.model.PickupStatus;
import com.foodredistribution.service.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickups")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    @GetMapping
    public List<Pickup> getAll(@RequestParam(required = false) Long ngoId) {
        return ngoId != null ? pickupService.getByNgo(ngoId) : pickupService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pickup> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pickupService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Pickup> create(@RequestBody Pickup pickup) {
        return ResponseEntity.ok(pickupService.create(pickup));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pickup> updateStatus(@PathVariable Long id, @RequestParam PickupStatus status) {
        return ResponseEntity.ok(pickupService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pickupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
