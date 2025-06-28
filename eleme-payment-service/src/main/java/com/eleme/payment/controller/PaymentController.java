package com.eleme.payment.controller;

import com.eleme.payment.entity.Payment;
import com.eleme.payment.result.Result;
import com.eleme.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/pay")
    @CircuitBreaker(name = "payment-service", fallbackMethod = "processPaymentFallback")
    public Result processPayment(@RequestParam("orderId") Integer orderId,
                                 @RequestParam(value = "paymentMethod", defaultValue = "alipay") String paymentMethod) {
        Payment payment = paymentService.processPayment(orderId, paymentMethod);
        if (payment.getPaymentStatus() == 1) {
            return Result.success(payment);
        } else {
            return Result.error("支付失败，请重试");
        }
    }

    @GetMapping("/payment/status/{orderId}")
    @CircuitBreaker(name = "payment-service", fallbackMethod = "getPaymentStatusFallback")
    public Result getPaymentStatus(@PathVariable("orderId") Integer orderId) {
        Payment payment = paymentService.getPaymentStatus(orderId);
        return Result.success(payment);
    }

    @PostMapping("/payment/refund/{orderId}")
    @CircuitBreaker(name = "payment-service", fallbackMethod = "refundFallback")
    public Result refund(@PathVariable("orderId") Integer orderId) {
        boolean success = paymentService.refund(orderId);
        if (success) {
            return Result.success("退款成功");
        } else {
            return Result.error("退款失败");
        }
    }

    // Fallback methods
    public Result processPaymentFallback(@RequestParam("orderId") Integer orderId,
                                         @RequestParam("paymentMethod") String paymentMethod,
                                         Exception ex) {
        return Result.error(-1, "支付服务暂时不可用，请稍后重试");
    }

    public Result getPaymentStatusFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "支付服务暂时不可用，请稍后重试");
    }

    public Result refundFallback(@PathVariable("orderId") Integer orderId, Exception ex) {
        return Result.error(-1, "支付服务暂时不可用，请稍后重试");
    }
}
