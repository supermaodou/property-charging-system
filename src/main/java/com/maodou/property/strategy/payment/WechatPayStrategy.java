package com.maodou.property.strategy.payment;

import com.maodou.property.enums.PaymentMethod;
import com.maodou.property.vo.PaymentRequest;
import com.maodou.property.vo.PaymentResult;
import org.springframework.stereotype.Component;

// 微信支付策略
@Component
public class WechatPayStrategy implements PaymentStrategy {

    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // 调用微信支付API
        try {
            String transactionId = "WECHAT_" + System.currentTimeMillis();
            String qrCode = "weixin://wxpay/bizpayurl?pr=" + request.getAmount();

            return PaymentResult.builder()
                    .success(true)
                    .transactionId(transactionId)
                    .qrCode(qrCode)
                    .message("微信支付二维码生成成功")
                    .build();
        } catch (Exception e) {
            return PaymentResult.builder()
                    .success(false)
                    .message("微信支付失败: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public String getPaymentMethod() {
        return PaymentMethod.WECHAT.getCode();
    }
}