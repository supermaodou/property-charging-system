package com.maodou.property.strategy.payment;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 5. 支付策略工厂
@Component
public class PaymentStrategyFactory {

    private final Map<String, PaymentStrategy> strategies;

    public PaymentStrategyFactory(List<PaymentStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        PaymentStrategy::getPaymentMethod,
                        strategy -> strategy
                ));
    }

    public PaymentStrategy getStrategy(String paymentMethod) {
        PaymentStrategy strategy = strategies.get(paymentMethod);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付方式: " + paymentMethod);
        }
        return strategy;
    }
}