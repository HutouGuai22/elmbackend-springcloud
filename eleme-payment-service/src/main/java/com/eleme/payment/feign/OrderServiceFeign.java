package com.eleme.payment.feign;

import com.eleme.payment.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eleme-order-service", fallbackFactory = OrderServiceFeignFallbackFactory.class)
public interface OrderServiceFeign {

    @PutMapping("/orders/updateState")
    Result updateOrderState(@RequestParam("orderId") Integer orderId, @RequestParam("orderState") Integer orderState);
}
