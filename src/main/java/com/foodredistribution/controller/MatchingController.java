package com.foodredistribution.controller;

import com.foodredistribution.model.Matching;
import com.foodredistribution.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping
    public List<Matching> getAll(
            @RequestParam(required = false) Long donationId,
            @RequestParam(required = false) Long ngoId) {
        if (donationId != null) return matchingService.getRankedMatchesForDonation(donationId);
        if (ngoId != null) return matchingService.getByNgo(ngoId);
        return matchingService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matching> getById(@PathVariable Long id) {
        return ResponseEntity.ok(matchingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Matching> create(@RequestBody Matching matching) {
        return ResponseEntity.ok(matchingService.create(matching));
    }

    /** NGO clicks "Accept" — this only records the click; the winner is decided a few seconds later. */
    @PatchMapping("/{id}/accept")
    public ResponseEntity<Matching> accept(@PathVariable Long id) {
        return ResponseEntity.ok(matchingService.recordAccept(id));
    }

    @PatchMapping("/{id}/decline")
    public ResponseEntity<Matching> decline(@PathVariable Long id) {
        return ResponseEntity.ok(matchingService.recordDecline(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matchingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
