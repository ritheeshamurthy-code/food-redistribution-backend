package com.foodredistribution.controller;

import com.foodredistribution.model.EligibilityScreening;
import com.foodredistribution.service.EligibilityScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
@RequiredArgsConstructor
public class EligibilityScreeningController {

    private final EligibilityScreeningService screeningService;

    @GetMapping
    public List<EligibilityScreening> getAll() {
        return screeningService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EligibilityScreening> getById(@PathVariable Long id) {
        return ResponseEntity.ok(screeningService.getById(id));
    }

    @GetMapping("/donation/{donationId}")
    public ResponseEntity<EligibilityScreening> getByDonation(@PathVariable Long donationId) {
        return ResponseEntity.ok(screeningService.getByDonation(donationId));
    }

    @PostMapping
    public ResponseEntity<EligibilityScreening> create(@RequestBody EligibilityScreening screening) {
        return ResponseEntity.ok(screeningService.create(screening));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screeningService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
