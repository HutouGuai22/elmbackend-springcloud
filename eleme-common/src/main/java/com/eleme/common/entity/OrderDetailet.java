package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orderdetailet")
public class OrderDetailet {
    @TableId(type = IdType.AUTO)
    private Integer odId;

    @TableField("orderId")
    private Integer orderId;

    @TableField("foodId")
    private Integer foodId;

    @TableField("quantity")
    private Integer quantity;

    @TableField(exist = false)
    private Food food;
}
