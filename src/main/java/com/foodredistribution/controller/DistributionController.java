package com.foodredistribution.controller;

import com.foodredistribution.model.Distribution;
import com.foodredistribution.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
@RequiredArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @GetMapping
    public List<Distribution> getAll(@RequestParam(required = false) Long ngoId) {
        return ngoId != null ? distributionService.getByNgo(ngoId) : distributionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distribution> getById(@PathVariable Long id) {
        return ResponseEntity.ok(distributionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Distribution> create(@RequestBody Distribution distribution) {
        return ResponseEntity.ok(distributionService.create(distribution));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        distributionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
