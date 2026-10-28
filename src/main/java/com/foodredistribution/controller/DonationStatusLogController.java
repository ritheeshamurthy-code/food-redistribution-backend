package com.foodredistribution.controller;

import com.foodredistribution.model.DonationStatusLog;
import com.foodredistribution.service.DonationStatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-logs")
@RequiredArgsConstructor
public class DonationStatusLogController {

    private final DonationStatusLogService logService;

    @GetMapping
    public List<DonationStatusLog> getAll(@RequestParam(required = false) Long donationId) {
        return donationId != null ? logService.getHistoryForDonation(donationId) : logService.getAll();
    }
}
