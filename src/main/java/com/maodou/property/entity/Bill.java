package com.maodou.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 费用账单实体
@Data
@TableName("bills")
public class Bill {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("room_id")
    private Long roomId;

    @TableField("bill_type")
    private String billType; // 对应FeeType枚举

    @TableField("amount")
    private BigDecimal amount;

    @TableField("bill_month")
    private String billMonth; // 账单月份 yyyy-MM

    @TableField("status")
    private Integer status; // 对应BillStatus枚举

    @TableField("due_date")
    private LocalDateTime dueDate;

    @TableField("usage_amount")
    private BigDecimal usageAmount; // 用量(水电气等)

    @TableField("unit_price")
    private BigDecimal unitPrice; // 单价

    @TableField("remark")
    private String remark; // 备注

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("pay_time")
    private LocalDateTime payTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}