package com.foodredistribution.controller;

import com.foodredistribution.model.Ngo;
import com.foodredistribution.model.VerificationStatus;
import com.foodredistribution.service.NgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ngos")
@RequiredArgsConstructor
public class NgoController {

    private final NgoService ngoService;

    @GetMapping
    public List<Ngo> getAll(@RequestParam(required = false) VerificationStatus status) {
        return status != null ? ngoService.getByVerificationStatus(status) : ngoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ngo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ngoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Ngo> create(@RequestBody Ngo ngo) {
        return ResponseEntity.ok(ngoService.create(ngo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ngo> update(@PathVariable Long id, @RequestBody Ngo ngo) {
        return ResponseEntity.ok(ngoService.update(id, ngo));
    }

    /** Admin action: approve or reject an NGO's verification. */
    @PatchMapping("/{id}/verify")
    public ResponseEntity<Ngo> verify(@PathVariable Long id, @RequestParam VerificationStatus status) {
        return ResponseEntity.ok(ngoService.verify(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ngoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
