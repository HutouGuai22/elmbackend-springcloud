package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Orders {
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    @TableField("userId")
    private Integer userId;

    @TableField("businessId")
    private Integer businessId;

    @TableField("orderDate")
    private LocalDateTime orderDate;

    @TableField("orderTotal")
    private Double orderTotal;

    @TableField("daId")
    private Integer daId;

    @TableField("orderState")
    private Integer orderState;
}
