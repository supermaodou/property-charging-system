package com.maodou.property.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 账单状态枚举
@Getter
@AllArgsConstructor
public enum BillStatus {
    UNPAID(0, "未缴费"),
    PAID(1, "已缴费"),
    OVERDUE(2, "已逾期"),
    CANCELLED(3, "已取消");
    
    private final Integer code;
    private final String description;
}