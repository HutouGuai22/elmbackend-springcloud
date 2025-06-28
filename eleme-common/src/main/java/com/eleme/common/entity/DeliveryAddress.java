package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("deliveryaddress")
public class DeliveryAddress {
    @TableId(type = IdType.AUTO)
    private Integer daId;

    @TableField("contactName")
    private String contactName;

    @TableField("contactSex")
    private Integer contactSex;

    @TableField("contactTel")
    private String contactTel;

    @TableField("address")
    private String address;

    @TableField("userId")
    private Integer userId;
}
