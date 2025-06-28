package com.eleme.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @TableField("userName")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("userSex")
    private Integer userSex;

    @TableField("userImg")
    private String userImg;

    @TableField("delTag")
    private Integer delTag;
}
