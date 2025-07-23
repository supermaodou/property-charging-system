package com.maodou.property.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

// 支付结果
@Data
@Builder
public class PaymentResult {
    private boolean success;
    private String transactionId;
    private String payUrl; // 支付链接
    private String qrCode; // 二维码
    private String message;
    private Map<String, Object> extraData;
}