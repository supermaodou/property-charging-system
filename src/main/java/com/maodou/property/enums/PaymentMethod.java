package com.maodou.property.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 支付方式枚举
@Getter
@AllArgsConstructor
public enum PaymentMethod {
    ALIPAY("ALIPAY", "支付宝"),
    WECHAT("WECHAT", "微信支付"),
    BANK_CARD("BANK_CARD", "银行卡"),
    UNION_PAY("UNION_PAY", "银联支付");
    
    private final String code;
    private final String description;
}