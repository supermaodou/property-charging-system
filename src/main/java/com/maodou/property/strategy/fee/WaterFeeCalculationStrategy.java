package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 水费计算策略
@Component
public class WaterFeeCalculationStrategy implements FeeCalculationStrategy {

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 阶梯水费计算
        BigDecimal usage = context.getWaterUsage();
        BigDecimal totalFee = BigDecimal.ZERO;

        if (usage.compareTo(new BigDecimal("20")) <= 0) {
            // 20吨以内，3元/吨
            totalFee = usage.multiply(new BigDecimal("3"));
        } else if (usage.compareTo(new BigDecimal("50")) <= 0) {
            // 20-50吨，4元/吨
            totalFee = new BigDecimal("20").multiply(new BigDecimal("3"))
                    .add(usage.subtract(new BigDecimal("20")).multiply(new BigDecimal("4")));
        } else {
            // 50吨以上，5元/吨
            totalFee = new BigDecimal("60") // 前20吨
                    .add(new BigDecimal("120")) // 20-50吨
                    .add(usage.subtract(new BigDecimal("50")).multiply(new BigDecimal("5")));
        }

        return totalFee;
    }

    @Override
    public String getFeeType() {
        return FeeType.WATER_FEE.getCode();
    }
}