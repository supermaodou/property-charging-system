package com.maodou.property.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private Long billId;
    private Long userId;
    private String paymentMethod;
    private String notifyUrl;
    private String returnUrl;
}