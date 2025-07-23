package com.maodou.property.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long billId;
    private String paymentMethod;
    private String notifyUrl;
    private String returnUrl;
}