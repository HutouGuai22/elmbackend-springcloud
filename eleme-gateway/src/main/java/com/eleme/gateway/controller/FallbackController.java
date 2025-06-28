package com.eleme.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/user")
    public Mono<Map<String, Object>> userFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", -1);
        result.put("msg", "用户服务暂时不可用，请稍后重试");
        result.put("data", null);
        return Mono.just(result);
    }

    @RequestMapping("/fallback/business")
    public Mono<Map<String, Object>> businessFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", -1);
        result.put("msg", "商家服务暂时不可用，请稍后重试");
        result.put("data", null);
        return Mono.just(result);
    }

    @RequestMapping("/fallback/order")
    public Mono<Map<String, Object>> orderFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", -1);
        result.put("msg", "订单服务暂时不可用，请稍后重试");
        result.put("data", null);
        return Mono.just(result);
    }

    @RequestMapping("/fallback/payment")
    public Mono<Map<String, Object>> paymentFallback() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", -1);
        result.put("msg", "支付服务暂时不可用，请稍后重试");
        result.put("data", null);
        return Mono.just(result);
    }
}
