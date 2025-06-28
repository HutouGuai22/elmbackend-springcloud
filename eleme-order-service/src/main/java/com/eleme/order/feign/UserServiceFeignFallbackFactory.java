package com.eleme.order.feign;

import com.eleme.common.entity.DeliveryAddress;
import com.eleme.common.result.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFeignFallbackFactory implements FallbackFactory<UserServiceFeign> {

    @Override
    public UserServiceFeign create(Throwable cause) {
        return new UserServiceFeign() {
            @Override
            public Result<DeliveryAddress> getAddressById(Integer daId) {
                return Result.error(-1, "地址服务暂时不可用: " + cause.getMessage());
            }
        };
    }
}
