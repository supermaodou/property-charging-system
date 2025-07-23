package com.maodou.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

// 用户实体
@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("phone")
    private String phone;

    @TableField("id_card")
    private String idCard;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}