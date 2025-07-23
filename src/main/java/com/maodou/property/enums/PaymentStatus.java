package com.maodou.property.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 支付状态枚举
@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PENDING(0, "待支付"),
    SUCCESS(1, "支付成功"),
    FAILED(2, "支付失败"),
    CANCELLED(3, "已取消");
    
    private final Integer code;
    private final String description;
}