package com.saas.subscription.controller;

import com.saas.subscription.dto.SubscriptionDTO;
import com.saas.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return new ResponseEntity<>(subscriptionService.createSubscription(subscriptionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionByUserId(userId));
    }

    @GetMapping("/{userId}/history")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptionsByUserId(userId));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(userId, subscriptionDTO));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public ResponseEntity<Void> cancelSubscription(
            @PathVariable Long userId,
            @RequestParam(required = false) String reason) {
        subscriptionService.cancelSubscription(userId, reason);
        return ResponseEntity.noContent().build();
    }
} 