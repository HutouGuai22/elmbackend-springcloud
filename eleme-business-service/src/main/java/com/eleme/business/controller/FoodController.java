package com.eleme.business.controller;

import com.eleme.business.service.FoodService;
import com.eleme.business.vo.FoodVO;
import com.eleme.common.entity.Food;
import com.eleme.common.result.Result;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/businessId/{businessId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getFoodByBusinessIdFallback")
    public Result getFoodByBusinessId(@PathVariable("businessId") Integer businessId) {
        List<FoodVO> foods = foodService.getFoodByBusinessId(businessId);
        return Result.success(foods);
    }

    @GetMapping("/food/{businessId}/{userId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getFoodByBusinessIdAndUserIdFallback")
    public Result getFoodByBusinessIdAndUserId(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId) {
        List<FoodVO> foods = foodService.getFoodByBusinessIdAndUserId(businessId, userId);
        return Result.success(foods);
    }

    @GetMapping("/food/foodId/{foodId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getFoodByIdFallback")
    public Result getFoodById(@PathVariable("foodId") Integer foodId) {
        Food food = foodService.getFoodById(foodId);
        if (food != null) {
            return Result.success(food);
        }
        return Result.error("商品不存在");
    }

    // Fallback methods
    public Result getFoodByBusinessIdFallback(@PathVariable("businessId") Integer businessId, Exception ex) {
        return Result.error(-1, "商品服务暂时不可用，请稍后重试");
    }

    public Result getFoodByBusinessIdAndUserIdFallback(@PathVariable("businessId") Integer businessId, @PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "商品服务暂时不可用，请稍后重试");
    }

    public Result getFoodByIdFallback(@PathVariable("foodId") Integer foodId, Exception ex) {
        return Result.error(-1, "商品服务暂时不可用，请稍后重试");
    }
}
