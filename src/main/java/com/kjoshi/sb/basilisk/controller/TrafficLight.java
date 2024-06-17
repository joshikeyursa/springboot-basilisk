package com.kjoshi.sb.basilisk.controller;

import com.kjoshi.sb.basilisk.service.APIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TrafficLight {
    private final APIService apiService;
    private static final Logger logger = LoggerFactory.getLogger(TrafficLight.class);
    public TrafficLight(APIService apiService){
        this.apiService = apiService;
    }
    @GetMapping("/checkSignal")
    public Mono<String> checkSignal(){
        logger.info("Got the request");
        return apiService.checkSignal();
    }@GetMapping("/checkSignalSync")
    public String checkSignalSync(){
        logger.info("Got the request for Sync");
        return apiService.checkSyncSignal();
    }
}
