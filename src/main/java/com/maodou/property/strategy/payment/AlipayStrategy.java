package com.maodou.property.strategy.payment;

import com.maodou.property.enums.PaymentMethod;
import com.maodou.property.vo.PaymentRequest;
import com.maodou.property.vo.PaymentResult;
import org.springframework.stereotype.Component;

// 支付宝支付策略
@Component
public class AlipayStrategy implements PaymentStrategy {

    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // 调用支付宝API
        try {
            // 这里模拟支付宝支付流程
            String transactionId = "ALIPAY_" + System.currentTimeMillis();
            String payUrl = "https://openapi.alipay.com/gateway.do?..." + request.getAmount();

            return PaymentResult.builder()
                    .success(true)
                    .transactionId(transactionId)
                    .payUrl(payUrl)
                    .message("支付宝支付链接生成成功")
                    .build();
        } catch (Exception e) {
            return PaymentResult.builder()
                    .success(false)
                    .message("支付宝支付失败: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public String getPaymentMethod() {
        return PaymentMethod.ALIPAY.getCode();
    }
}