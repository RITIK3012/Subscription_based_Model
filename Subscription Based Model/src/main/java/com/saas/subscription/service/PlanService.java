package com.saas.subscription.service;

import com.saas.subscription.dto.PlanDTO;
import java.util.List;

public interface PlanService {
    List<PlanDTO> getAllActivePlans();
    PlanDTO getPlanById(Long id);
    PlanDTO createPlan(PlanDTO planDTO);
    PlanDTO updatePlan(Long id, PlanDTO planDTO);
    void deletePlan(Long id);
} 