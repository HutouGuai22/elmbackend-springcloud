package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cart")
public class Cart {
    @TableId(type = IdType.AUTO)
    private Integer cartId;

    @TableField("foodId")
    private Integer foodId;

    @TableField("businessId")
    private Integer businessId;

    @TableField("userId")
    private Integer userId;

    @TableField("quantity")
    private Integer quantity;
}
