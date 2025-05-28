package com.saas.subscription.service.impl;

import com.saas.subscription.dto.SubscriptionDTO;
import com.saas.subscription.model.Plan;
import com.saas.subscription.model.Subscription;
import com.saas.subscription.model.SubscriptionStatus;
import com.saas.subscription.repository.PlanRepository;
import com.saas.subscription.repository.SubscriptionRepository;
import com.saas.subscription.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        Plan plan = planRepository.findById(subscriptionDTO.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + subscriptionDTO.getPlanId()));

        // Check if user already has an active subscription
        subscriptionRepository.findByUserIdAndStatus(subscriptionDTO.getUserId(), SubscriptionStatus.ACTIVE)
                .ifPresent(s -> {
                    throw new IllegalStateException("User already has an active subscription");
                });

        Subscription subscription = new Subscription();
        subscription.setUserId(subscriptionDTO.getUserId());
        subscription.setPlan(plan);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusDays(plan.getDuration()));

        return convertToDTO(subscriptionRepository.save(subscription));
    }

    @Override
    public SubscriptionDTO getSubscriptionByUserId(Long userId) {
        return subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("No active subscription found for user: " + userId));
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDTO updateSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        Subscription currentSubscription = subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("No active subscription found for user: " + userId));

        Plan newPlan = planRepository.findById(subscriptionDTO.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + subscriptionDTO.getPlanId()));

        // Set the current subscription to inactive
        currentSubscription.setStatus(SubscriptionStatus.INACTIVE);
        subscriptionRepository.save(currentSubscription);

        // Create new subscription with new plan
        Subscription newSubscription = new Subscription();
        newSubscription.setUserId(userId);
        newSubscription.setPlan(newPlan);
        newSubscription.setStatus(SubscriptionStatus.ACTIVE);
        newSubscription.setStartDate(LocalDateTime.now());
        newSubscription.setEndDate(LocalDateTime.now().plusDays(newPlan.getDuration()));

        return convertToDTO(subscriptionRepository.save(newSubscription));
    }

    @Override
    public void cancelSubscription(Long userId, String reason) {
        Subscription subscription = subscriptionRepository.findByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("No active subscription found for user: " + userId));

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setCancelReason(reason);
        subscription.setEndDate(LocalDateTime.now());
        
        subscriptionRepository.save(subscription);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void checkAndUpdateExpiredSubscriptions() {
        List<Subscription> expiredSubscriptions = subscriptionRepository
                .findByStatusAndEndDateBefore(SubscriptionStatus.ACTIVE, LocalDateTime.now());

        expiredSubscriptions.forEach(subscription -> {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(subscription);
        });
    }

    private SubscriptionDTO convertToDTO(Subscription subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        BeanUtils.copyProperties(subscription, dto);
        dto.setPlanId(subscription.getPlan().getId());
        return dto;
    }
} 