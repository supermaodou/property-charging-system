package com.maodou.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 支付记录实体
@Data
@TableName("payments")
public class Payment {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("bill_id")
    private Long billId;

    @TableField("room_id")
    private Long roomId;

    @TableField("user_id")
    private Long userId; // 支付用户ID

    @TableField("amount")
    private BigDecimal amount;

    @TableField("payment_method")
    private String paymentMethod; // 对应PaymentMethod枚举

    @TableField("transaction_id")
    private String transactionId;

    @TableField("status")
    private Integer status; // 对应PaymentStatus枚举

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("pay_time")
    private LocalDateTime payTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}