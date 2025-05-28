package com.saas.subscription.controller;

import com.saas.subscription.dto.PlanDTO;
import com.saas.subscription.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> getAllActivePlans() {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanDTO> createPlan(@Valid @RequestBody PlanDTO planDTO) {
        return new ResponseEntity<>(planService.createPlan(planDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable Long id, @Valid @RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.updatePlan(id, planDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
} 