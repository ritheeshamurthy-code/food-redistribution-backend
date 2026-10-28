
package com.foodredistribution.controller;

import com.foodredistribution.model.Donor;
import com.foodredistribution.service.DonorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @GetMapping
    public ResponseEntity<List<Donor>> getAll() {
        return ResponseEntity.ok(donorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(donorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Donor> create(
            @RequestBody Donor donor) {

        Donor savedDonor = donorService.create(donor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDonor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donor> update(
            @PathVariable Long id,
            @RequestBody Donor donor) {

        Donor updatedDonor = donorService.update(id, donor);

        return ResponseEntity.ok(updatedDonor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        donorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
