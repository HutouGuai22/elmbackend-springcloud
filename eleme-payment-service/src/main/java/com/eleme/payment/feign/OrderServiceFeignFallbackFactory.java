package com.eleme.payment.feign;

import com.eleme.payment.result.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignFallbackFactory implements FallbackFactory<OrderServiceFeign> {

    @Override
    public OrderServiceFeign create(Throwable cause) {
        return new OrderServiceFeign() {
            @Override
            public Result updateOrderState(Integer orderId, Integer orderState) {
                return Result.error(-1, "订单服务暂时不可用: " + cause.getMessage());
            }
        };
    }
}
