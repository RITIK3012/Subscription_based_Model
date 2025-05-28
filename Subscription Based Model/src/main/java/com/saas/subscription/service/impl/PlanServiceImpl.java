package com.saas.subscription.service.impl;

import com.saas.subscription.dto.PlanDTO;
import com.saas.subscription.model.Plan;
import com.saas.subscription.repository.PlanRepository;
import com.saas.subscription.service.PlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<PlanDTO> getAllActivePlans() {
        return planRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlanDTO getPlanById(Long id) {
        return planRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + id));
    }

    @Override
    public PlanDTO createPlan(PlanDTO planDTO) {
        Plan plan = convertToEntity(planDTO);
        plan.setActive(true);
        return convertToDTO(planRepository.save(plan));
    }

    @Override
    public PlanDTO updatePlan(Long id, PlanDTO planDTO) {
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + id));
        
        BeanUtils.copyProperties(convertToEntity(planDTO), existingPlan, "id");
        return convertToDTO(planRepository.save(existingPlan));
    }

    @Override
    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + id));
        plan.setActive(false);
        planRepository.save(plan);
    }

    private PlanDTO convertToDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        BeanUtils.copyProperties(plan, dto);
        return dto;
    }

    private Plan convertToEntity(PlanDTO dto) {
        Plan plan = new Plan();
        BeanUtils.copyProperties(dto, plan);
        return plan;
    }
} 