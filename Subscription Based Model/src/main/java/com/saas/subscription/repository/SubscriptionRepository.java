package com.saas.subscription.repository;

import com.saas.subscription.model.Subscription;
import com.saas.subscription.model.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByStatusAndEndDateBefore(SubscriptionStatus status, LocalDateTime date);
} 