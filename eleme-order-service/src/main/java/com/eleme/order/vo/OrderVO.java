package com.eleme.order.vo;

import com.eleme.common.entity.Business;
import com.eleme.common.entity.DeliveryAddress;
import com.eleme.common.entity.OrderDetailet;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO {
    private Integer orderId;
    private LocalDateTime orderDate;
    private Double orderTotal;
    private Integer orderState;
    private Business business;
    private DeliveryAddress deliveryAddress;
    private List<OrderDetailet> orderDetailetList;
}
