package com.eleme.payment.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    private Integer paymentId;
    private Integer orderId;
    private Integer userId;
    private Double amount;
    private String paymentMethod; // alipay, wechat
    private Integer paymentStatus; // 0-待支付, 1-支付成功, 2-支付失败
    private LocalDateTime paymentTime;
    private String transactionId;
}
