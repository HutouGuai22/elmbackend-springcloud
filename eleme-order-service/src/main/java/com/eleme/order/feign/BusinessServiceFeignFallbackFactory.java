package com.eleme.order.feign;

import com.eleme.common.entity.Business;
import com.eleme.common.entity.Cart;
import com.eleme.common.entity.Food;
import com.eleme.common.result.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessServiceFeignFallbackFactory implements FallbackFactory<BusinessServiceFeign> {

    @Override
    public BusinessServiceFeign create(Throwable cause) {
        return new BusinessServiceFeign() {
            @Override
            public Result<Business> getBusinessById(Integer businessId) {
                return Result.error(-1, "商家服务暂时不可用: " + cause.getMessage());
            }

            @Override
            public Result<Food> getFoodById(Integer foodId) {
                return Result.error(-1, "商品服务暂时不可用: " + cause.getMessage());
            }

            @Override
            public Result<List<Cart>> getCartByUserIdAndBusinessId(Integer userId, Integer businessId) {
                return Result.error(-1, "购物车服务暂时不可用: " + cause.getMessage());
            }

            @Override
            public Result clearCart(Integer userId, Integer businessId) {
                return Result.error(-1, "购物车服务暂时不可用: " + cause.getMessage());
            }
        };
    }
}
