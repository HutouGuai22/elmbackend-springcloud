package com.eleme.user.controller;

import com.eleme.common.entity.DeliveryAddress;
import com.eleme.common.result.Result;
import com.eleme.user.service.DeliveryAddressService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @GetMapping("/addressList/{userId}")
    @CircuitBreaker(name = "user-service", fallbackMethod = "getAddressListFallback")
    public Result getAddressList(@PathVariable("userId") Integer userId) {
        List<DeliveryAddress> addresses = deliveryAddressService.getAddressByUserId(userId);
        return Result.success(addresses);
    }

    @GetMapping("/addressList/daId/{daId}")
    @CircuitBreaker(name = "user-service", fallbackMethod = "getAddressByIdFallback")
    public Result getAddressById(@PathVariable("daId") Integer daId) {
        DeliveryAddress address = deliveryAddressService.getAddressById(daId);
        return Result.success(address);
    }

    @PostMapping("/addressList")
    @CircuitBreaker(name = "user-service", fallbackMethod = "addAddressFallback")
    public Result addAddress(@RequestBody DeliveryAddress address) {
        boolean success = deliveryAddressService.addAddress(address);
        if (success) {
            return Result.success();
        }
        return Result.error("添加地址失败");
    }

    @PutMapping("/addressList")
    @CircuitBreaker(name = "user-service", fallbackMethod = "updateAddressFallback")
    public Result updateAddress(@RequestBody DeliveryAddress address) {
        boolean success = deliveryAddressService.updateAddress(address);
        if (success) {
            return Result.success();
        }
        return Result.error("更新地址失败");
    }

    @DeleteMapping("/addressList/{daId}")
    @CircuitBreaker(name = "user-service", fallbackMethod = "deleteAddressFallback")
    public Result deleteAddress(@PathVariable("daId") Integer daId) {
        boolean success = deliveryAddressService.deleteAddress(daId);
        if (success) {
            return Result.success();
        }
        return Result.error("删除地址失败");
    }

    // Fallback methods
    public Result getAddressListFallback(@PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "地址服务暂时不可用，请稍后重试");
    }

    public Result getAddressByIdFallback(@PathVariable("daId") Integer daId, Exception ex) {
        return Result.error(-1, "地址服务暂时不可用，请稍后重试");
    }

    public Result addAddressFallback(@RequestBody DeliveryAddress address, Exception ex) {
        return Result.error(-1, "地址服务暂时不可用，请稍后重试");
    }

    public Result updateAddressFallback(@RequestBody DeliveryAddress address, Exception ex) {
        return Result.error(-1, "地址服务暂时不可用，请稍后重试");
    }

    public Result deleteAddressFallback(@PathVariable("daId") Integer daId, Exception ex) {
        return Result.error(-1, "地址服务暂时不可用，请稍后重试");
    }
}
