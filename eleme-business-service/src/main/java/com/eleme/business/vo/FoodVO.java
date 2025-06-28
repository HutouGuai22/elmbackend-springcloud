package com.eleme.business.vo;

import com.eleme.common.entity.Cart;
import lombok.Data;

@Data
public class FoodVO {
    private Integer foodId;
    private String foodName;
    private String foodExplain;
    private String foodImg;
    private Double foodPrice;
    private Integer businessId;
    private String remarks;
    private Cart cart;
}
