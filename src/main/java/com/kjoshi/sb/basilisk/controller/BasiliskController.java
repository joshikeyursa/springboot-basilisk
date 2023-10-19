package com.kjoshi.sb.basilisk.controller;

import com.kjoshi.sb.basilisk.bean.KeyValueReqBean;
import com.kjoshi.sb.basilisk.bean.KeyValueResBean;
import com.kjoshi.sb.basilisk.service.BasiliskCacheService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/basilisk")
public class BasiliskController {

    BasiliskCacheService basiliskCacheService;
    BasiliskController(BasiliskCacheService basiliskCacheService){
        this.basiliskCacheService = basiliskCacheService;
    }
    @GetMapping("/health")
    public Mono<String> healthCheck(){
        return basiliskCacheService.addKeyValueInCache("LAST_CHECK", new Date().toString())
                .thenReturn("SUCCESS");
    }

    @GetMapping("/cache/keys")
    public Flux<String > fetchAllCacheKeys(){
        return basiliskCacheService.fetchAllKeys();
    }
    @GetMapping("/cache/key/{key}")
    public Flux<KeyValueResBean> fetchCacheData(@PathVariable("key")String key){
        return basiliskCacheService.fetchKeyValuePair(key);
    }
    @GetMapping("/cache/keyValues")
    public Flux<KeyValueResBean> fetchAllCacheKeyValueMaps(){
        return basiliskCacheService.fetchAllKeyValuePairs();
    }
    @GetMapping("/cache/values")
    public Flux<String> fetchAllValues(){
        return basiliskCacheService.fetchAllValues();
    }

    @PostMapping("/cache/keyValue")
    public Mono<Flux<KeyValueResBean>> addKeyValuePairInCache(@RequestBody KeyValueReqBean reqBean){
        return basiliskCacheService.addKeyValueInCache(reqBean.key(),reqBean.value()).
                thenReturn(basiliskCacheService.fetchKeyValuePair(reqBean.key()));
    }
}
