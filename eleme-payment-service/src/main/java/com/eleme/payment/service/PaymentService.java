package com.eleme.payment.service;

import com.eleme.payment.entity.Payment;
import com.eleme.payment.feign.OrderServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private OrderServiceFeign orderServiceFeign;
    
    public Payment processPayment(Integer orderId, String paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());
        
        // 模拟支付处理
        try {
            // 模拟支付延迟
            Thread.sleep(1000);
            
            // 模拟支付成功（实际项目中这里会调用真实的支付接口）
            payment.setPaymentStatus(1); // 支付成功
            
            // 更新订单状态
            orderServiceFeign.updateOrderState(orderId, 1);
            
        } catch (Exception e) {
            payment.setPaymentStatus(2); // 支付失败
        }
        
        return payment;
    }
    
    public Payment getPaymentStatus(Integer orderId) {
        // 模拟查询支付状态
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentStatus(1); // 假设支付成功
        payment.setPaymentTime(LocalDateTime.now());
        return payment;
    }
    
    public boolean refund(Integer orderId) {
        // 模拟退款处理
        try {
            Thread.sleep(500);
            // 更新订单状态为已退款
            orderServiceFeign.updateOrderState(orderId, 3);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
