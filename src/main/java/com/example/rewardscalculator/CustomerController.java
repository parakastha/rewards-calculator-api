package com.example.rewardscalculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRewardService customerRewardService;

    public CustomerController(CustomerRewardService customerRewardService) {
        this.customerRewardService = customerRewardService;
    }

    @GetMapping("/{id}/rewards")
    public Map<Month, Integer> getCustomerRewards(@PathVariable Long id) {
        return customerRewardService.calculateRewardPoints(id);
    }
}
