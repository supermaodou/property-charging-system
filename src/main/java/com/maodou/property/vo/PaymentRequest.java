package com.maodou.property.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

// 支付请求
@Data
@Builder
public class PaymentRequest {
    private Long billId;
    private Long userId;
    private BigDecimal amount;
    private String notifyUrl;
    private String returnUrl;
    private Map<String, String> extraParams;
}