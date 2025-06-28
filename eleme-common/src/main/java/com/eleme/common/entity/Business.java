package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("business")
public class Business {
    @TableId(type = IdType.AUTO)
    private Integer businessId;

    @TableField("businessName")
    private String businessName;

    @TableField("businessAddress")
    private String businessAddress;

    @TableField("businessExplain")
    private String businessExplain;

    @TableField("businessImg")
    private String businessImg;

    @TableField("orderTypeId")
    private Integer orderTypeId;

    @TableField("starPrice")
    private Double starPrice;

    @TableField("deliveryPrice")
    private Double deliveryPrice;

    @TableField("remarks")
    private String remarks;
}
