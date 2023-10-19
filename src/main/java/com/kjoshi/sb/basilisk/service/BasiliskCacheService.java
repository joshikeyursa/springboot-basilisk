package com.kjoshi.sb.basilisk.service;

import com.kjoshi.sb.basilisk.bean.KeyValueResBean;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BasiliskCacheService {

    ReactiveRedisOperations<String,String> redisOperations;
    BasiliskCacheService(ReactiveRedisOperations<String,String> redisOperations){
        this.redisOperations = redisOperations;
    }

    public Flux<String> fetchAllKeys(){
        return redisOperations.keys("*");
    }

    public Mono<Boolean> addKeyValueInCache(String key, String value){
        return redisOperations
                .opsForValue()
                .set(key,value)
                .thenReturn(true);
    }
    public Flux<KeyValueResBean> fetchAllKeyValuePairs(){
        return fetchKeyValuePair("*");
    }
    public Flux<KeyValueResBean> fetchKeyValuePair(String pattern){
        return redisOperations.keys(pattern)
                .map(key-> new KeyValueResBean(key, redisOperations.opsForValue().get(key)));
    }

    public Flux<String> fetchAllValues(){
        return redisOperations
                .keys("*")
                .flatMap(redisOperations.opsForValue()::get);
    }
}
