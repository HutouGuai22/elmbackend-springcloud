package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("food")
public class Food {
    @TableId(type = IdType.AUTO)
    private Integer foodId;

    @TableField("foodName")
    private String foodName;

    @TableField("foodExplain")
    private String foodExplain;

    @TableField("foodImg")
    private String foodImg;

    @TableField("foodPrice")
    private Double foodPrice;

    @TableField("businessId")
    private Integer businessId;

    @TableField("remarks")
    private String remarks;

    @TableField(exist = false)
    private Cart cart;
}
