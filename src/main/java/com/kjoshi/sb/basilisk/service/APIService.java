package com.kjoshi.sb.basilisk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class APIService {
    private @Value("${signal.action}")
    String callType;
    private @Value("${signal.response}")
    String response;
    private @Value("${downstream.path}")String downstreamPath;
    private @Value("${downstream.url}")String baseUrl;
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    public APIService(WebClient webClient,RestTemplate restTemplate){
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    public Mono<String> checkSignal(){
        logger.info("{}::{}",callType,response);
        if("RESPOND".equalsIgnoreCase(callType)){
            return Mono.just(response);
        }else{
            return webClient.get()
                    .uri(downstreamPath)
                    .retrieve()
                    .bodyToMono(String.class);
        }
    }

    public String checkSyncSignal(){
        logger.info("{}::{}",callType,response);
        if("RESPOND".equalsIgnoreCase(callType)){
            return response;
        }else{
            return restTemplate.getForEntity(baseUrl+"/checkSignalSync",String.class).getBody();
        }
    }
}
