package com.eleme.order.controller;

import com.eleme.common.entity.Business;
import com.eleme.common.entity.OrderDetailet;
import com.eleme.common.entity.Orders;
import com.eleme.common.result.Result;
import com.eleme.order.service.OrderService;
import com.eleme.order.vo.OrderVO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    @CircuitBreaker(name = "order-service", fallbackMethod = "createOrderFallback")
    public Result createOrder(@RequestBody Orders order) {
        try {
            Integer orderId = orderService.createOrder(order);
            return Result.success(orderId);
        } catch (Exception e) {
            return Result.error("创建订单失败");
        }
    }

    @GetMapping("/orders/{orderId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getOrderFallback")
    public Result getOrder(@PathVariable("orderId") Integer orderId) {
        OrderVO orderVO = orderService.getOrderWithDetails(orderId);
        if (orderVO != null) {
            return Result.success(orderVO);
        }
        return Result.error("订单不存在");
    }

    @PutMapping("/orders")
    @CircuitBreaker(name = "order-service", fallbackMethod = "updateOrderFallback")
    public Result updateOrder(@RequestBody Orders order) {
        boolean success = orderService.updateOrderState(order.getOrderId(), order.getOrderState());
        if (success) {
            return Result.success();
        }
        return Result.error("更新订单失败");
    }

    @DeleteMapping("/orders/{orderId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "deleteOrderFallback")
    public Result deleteOrder(@PathVariable("orderId") Integer orderId) {
        boolean success = orderService.deleteOrder(orderId);
        if (success) {
            return Result.success();
        }
        return Result.error("删除订单失败");
    }

    @GetMapping("/orderDetailet/{orderId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getOrderDetailsFallback")
    public Result getOrderDetails(@PathVariable("orderId") Integer orderId) {
        List<OrderDetailet> orderDetailets = orderService.getOrderDetailsByOrderId(orderId);
        return Result.success(orderDetailets);
    }

    @GetMapping("/order/businessInfo/{orderId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getBusinessInfoFallback")
    public Result getBusinessInfo(@PathVariable("orderId") Integer orderId) {
        Business business = orderService.getBusinessInfoByOrderId(orderId);
        if (business != null) {
            return Result.success(business);
        }
        return Result.error("获取商家信息失败");
    }

    // 历史订单功能 - 未支付订单
    @GetMapping("/orderListNotPay/{userId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getOrderListNotPayFallback")
    public Result getOrderListNotPay(@PathVariable("userId") Integer userId) {
        List<OrderVO> orders = orderService.getOrdersByUserIdAndState(userId, 0);
        return Result.success(orders);
    }

    // 历史订单功能 - 已支付订单
    @GetMapping("/orderListPay/{userId}")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getOrderListPayFallback")
    public Result getOrderListPay(@PathVariable("userId") Integer userId) {
        List<OrderVO> orders = orderService.getOrdersByUserIdAndState(userId, 1);
        return Result.success(orders);
    }

    @PutMapping("/orders/updateState")
    @CircuitBreaker(name = "order-service", fallbackMethod = "updateOrderStateFallback")
    public Result updateOrderState(@RequestParam("orderId") Integer orderId, @RequestParam("orderState") Integer orderState) {
        boolean success = orderService.updateOrderState(orderId, orderState);
        if (success) {
            return Result.success();
        }
        return Result.error("更新订单状态失败");
    }

    // Fallback methods
    public Result updateOrderStateFallback(@RequestParam("orderId") Integer orderId, @RequestParam("orderState") Integer orderState, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result createOrderFallback(@RequestBody Orders order, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result getOrderFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result updateOrderFallback(@RequestBody Orders order, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result deleteOrderFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result getOrderDetailsFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result getBusinessInfoFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "订单服务暂时不可用，请稍后重试");
    }

    public Result getOrderListNotPayFallback(@PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "历史订单服务暂时不可用，请稍后重试");
    }

    public Result getOrderListPayFallback(@PathVariable("userId") Integer userId, Exception ex) {
        return Result.error(-1, "历史订单服务暂时不可用，请稍后重试");
    }
}
