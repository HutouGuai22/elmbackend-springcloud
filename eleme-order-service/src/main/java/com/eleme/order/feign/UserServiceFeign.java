package com.eleme.order.feign;

import com.eleme.common.entity.DeliveryAddress;
import com.eleme.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eleme-user-service", fallbackFactory = UserServiceFeignFallbackFactory.class)
public interface UserServiceFeign {

    @GetMapping("/addressList/daId/{daId}")
    Result<DeliveryAddress> getAddressById(@PathVariable("daId") Integer daId);
}
