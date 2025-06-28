package com.eleme.business.controller;

import com.eleme.business.service.CartService;
import com.eleme.common.entity.Cart;
import com.eleme.common.result.Result;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/food/getCart/{foodId}/{userId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getCartFallback")
    public Result getCart(@PathVariable("foodId") Integer foodId, @PathVariable("userId") Integer userId) {
        Cart cart = cartService.getCartByFoodIdAndUserId(foodId, userId);
        return Result.success(cart);
    }

    @PostMapping("/cart")
    @CircuitBreaker(name = "business-service", fallbackMethod = "addCartFallback")
    public Result addCart(@RequestBody Cart cart) {
        boolean success = cartService.addCart(cart);
        if (success) {
            return Result.success();
        }
        return Result.error("添加购物车失败");
    }

    @PutMapping("/cart/addQuantity/{cartId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "addQuantityFallback")
    public Result addQuantity(@PathVariable("cartId") Integer cartId) {
        boolean success = cartService.addQuantity(cartId);
        if (success) {
            return Result.success();
        }
        return Result.error("增加数量失败");
    }

    @PutMapping("/cart/substrateQuantity/{cartId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "substrateQuantityFallback")
    public Result substrateQuantity(@PathVariable("cartId") Integer cartId) {
        boolean success = cartService.substrateQuantity(cartId);
        if (success) {
            return Result.success();
        }
        return Result.error("减少数量失败");
    }

    @GetMapping("/cart/{userId}/{businessId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "getCartByUserIdAndBusinessIdFallback")
    public Result getCartByUserIdAndBusinessId(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId) {
        List<Cart> carts = cartService.getCartByUserIdAndBusinessId(userId, businessId);
        return Result.success(carts);
    }

    @PostMapping("/cart/clear/{userId}/{businessId}")
    @CircuitBreaker(name = "business-service", fallbackMethod = "clearCartFallback")
    public Result clearCart(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId) {
        boolean success = cartService.clearCart(userId, businessId);
        if (success) {
            return Result.success();
        }
        return Result.error("清空购物车失败");
    }

    // Fallback methods
    public Result getCartFallback(@PathVariable("foodId") Integer foodId, @PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }

    public Result addCartFallback(@RequestBody Cart cart, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }

    public Result addQuantityFallback(@PathVariable("cartId") Integer cartId, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }

    public Result substrateQuantityFallback(@PathVariable("cartId") Integer cartId, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }

    public Result getCartByUserIdAndBusinessIdFallback(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }

    public Result clearCartFallback(@PathVariable("userId") Integer userId, @PathVariable("businessId") Integer businessId, Exception ex) {
        return Result.error(-1, "购物车服务暂时不可用，请稍后重试");
    }
}
