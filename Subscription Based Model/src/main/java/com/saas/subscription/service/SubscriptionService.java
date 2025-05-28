package com.saas.subscription.service;

import com.saas.subscription.dto.SubscriptionDTO;
import java.util.List;

public interface SubscriptionService {
    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO getSubscriptionByUserId(Long userId);
    List<SubscriptionDTO> getAllSubscriptionsByUserId(Long userId);
    SubscriptionDTO updateSubscription(Long userId, SubscriptionDTO subscriptionDTO);
    void cancelSubscription(Long userId, String reason);
    void checkAndUpdateExpiredSubscriptions();
} 