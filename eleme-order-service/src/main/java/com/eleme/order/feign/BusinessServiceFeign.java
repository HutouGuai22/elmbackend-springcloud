package com.eleme.order.feign;

import com.eleme.common.entity.Business;
import com.eleme.common.entity.Cart;
import com.eleme.common.entity.Food;
import com.eleme.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "eleme-business-service", fallbackFactory = BusinessServiceFeignFallbackFactory.class)
public interface BusinessServiceFeign {

    @GetMapping("/business/businessId/{businessId}")
    Result<Business> getBusinessById(@PathVariable("businessId") Integer businessId);

    @GetMapping("/food/foodId/{foodId}")
    Result<Food> getFoodById(@PathVariable("foodId") Integer foodId);

    @GetMapping("/cart/{userId}/{businessId}")
    Result<List<Cart>> getCartByUserIdAndBusinessId(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId);

    @PostMapping("/cart/clear/{userId}/{businessId}")
    Result clearCart(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId);
}
