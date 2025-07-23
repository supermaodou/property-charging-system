package com.maodou.property.strategy.fee;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 3. 费用计算工厂
@Component
public class FeeCalculationStrategyFactory {

    private final Map<String, FeeCalculationStrategy> strategies;

    public FeeCalculationStrategyFactory(List<FeeCalculationStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        FeeCalculationStrategy::getFeeType,
                        strategy -> strategy
                ));
    }

    public FeeCalculationStrategy getStrategy(String feeType) {
        FeeCalculationStrategy strategy = strategies.get(feeType);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的费用类型: " + feeType);
        }
        return strategy;
    }
}