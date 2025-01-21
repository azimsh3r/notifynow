package com.azimsh3r.rebalancerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class RebalancerScheduler {

    private final RebalancerService rebalancerService;

    public RebalancerScheduler(RebalancerService rebalancerService) {
        this.rebalancerService = rebalancerService;
    }

    @Scheduled(fixedRate = 100000)
    public void runRebalancer() throws JsonProcessingException {
        rebalancerService.rebalance();
    }
}
