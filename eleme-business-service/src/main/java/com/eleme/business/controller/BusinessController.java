package com.eleme.business.controller;

import com.eleme.business.service.BusinessService;
import com.eleme.common.entity.Business;
import com.eleme.common.result.Result;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/business")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getAllBusinessFallback")
    public Result getAllBusiness() {
        List<Business> businesses = businessService.getAllBusiness();
        return Result.success(businesses);
    }

    @GetMapping("/business/orderTypeId/{orderTypeId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getBusinessByOrderTypeIdFallback")
    public Result getBusinessByOrderTypeId(@PathVariable("orderTypeId") Integer orderTypeId) {
        List<Business> businesses = businessService.getBusinessByOrderTypeId(orderTypeId);
        return Result.success(businesses);
    }

    @GetMapping("/business/businessId/{businessId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getBusinessByIdFallback")
    public Result getBusinessById(@PathVariable("businessId") Integer businessId) {
        Business business = businessService.getBusinessById(businessId);
        if (business != null) {
            return Result.success(business);
        }
        return Result.error("商家不存在");
    }

    @GetMapping("/business/search/{keyword}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "searchBusinessFallback")
    public Result searchBusiness(@PathVariable("keyword") String keyword) {
        List<Business> businesses = businessService.searchBusiness(keyword);
        return Result.success(businesses);
    }

    @GetMapping("/business/total/{businessId}/{userId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getTotalFallback")
    public Result getTotal(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId) {
        Double total = businessService.getTotalByUserIdAndBusinessId(businessId, userId);
        return Result.success(total);
    }

    @GetMapping("/business/quantity/{businessId}/{userId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getQuantityFallback")
    public Result getQuantity(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId) {
        Integer quantity = businessService.getQuantityByUserIdAndBusinessId(businessId, userId);
        return Result.success(quantity);
    }

    // Fallback methods
    public Result getAllBusinessFallback(Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }

    public Result getBusinessByOrderTypeIdFallback(@PathVariable("orderTypeId") Integer orderTypeId, Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }

    public Result getBusinessByIdFallback(@PathVariable("businessId") Integer businessId, Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }

    public Result searchBusinessFallback(@PathVariable("keyword") String keyword, Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }

    public Result getTotalFallback(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }

    public Result getQuantityFallback(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "商家服务暂时不可用，请稍后重试");
    }
}
