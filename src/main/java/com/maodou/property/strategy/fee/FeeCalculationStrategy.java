package com.maodou.property.strategy.fee;

import java.math.BigDecimal;

// 2. 费用计算策略接口
public interface FeeCalculationStrategy {
    BigDecimal calculateFee(FeeCalculationContext context);
    String getFeeType();
}