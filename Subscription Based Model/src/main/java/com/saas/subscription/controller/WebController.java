package com.saas.subscription.controller;

import com.saas.subscription.service.PlanService;
import com.saas.subscription.service.SubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;

    public WebController(PlanService planService, SubscriptionService subscriptionService) {
        this.planService = planService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("plans", planService.getAllActivePlans());
        return "home";
    }

    @GetMapping("/view/plans")
    public String plans(Model model) {
        model.addAttribute("plans", planService.getAllActivePlans());
        return "plans";
    }

    @GetMapping("/subscriptions")
    public String subscriptions() {
        return "subscriptions";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
} 