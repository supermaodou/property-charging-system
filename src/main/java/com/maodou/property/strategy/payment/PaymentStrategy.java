package com.maodou.property.strategy.payment;

import com.maodou.property.vo.PaymentRequest;
import com.maodou.property.vo.PaymentResult;

// 4. 支付策略接口
public interface PaymentStrategy {
    PaymentResult processPayment(PaymentRequest request);
    String getPaymentMethod();
}