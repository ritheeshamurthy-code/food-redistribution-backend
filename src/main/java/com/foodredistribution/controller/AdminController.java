package com.foodredistribution.controller;

import com.foodredistribution.model.Admin;
import com.foodredistribution.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<Admin> getAll() {
        return adminService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.create(admin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.update(id, admin));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
