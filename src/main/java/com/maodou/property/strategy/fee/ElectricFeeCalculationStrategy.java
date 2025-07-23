package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 电费计算策略
@Component
public class ElectricFeeCalculationStrategy implements FeeCalculationStrategy {

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 阶梯电费计算
        BigDecimal usage = context.getElectricUsage();
        BigDecimal totalFee = BigDecimal.ZERO;

        if (usage.compareTo(new BigDecimal("200")) <= 0) {
            // 200度以内，0.5元/度
            totalFee = usage.multiply(new BigDecimal("0.5"));
        } else if (usage.compareTo(new BigDecimal("400")) <= 0) {
            // 200-400度，0.6元/度
            totalFee = new BigDecimal("200").multiply(new BigDecimal("0.5"))
                    .add(usage.subtract(new BigDecimal("200")).multiply(new BigDecimal("0.6")));
        } else {
            // 400度以上，0.8元/度
            totalFee = new BigDecimal("100") // 前200度
                    .add(new BigDecimal("120")) // 200-400度
                    .add(usage.subtract(new BigDecimal("400")).multiply(new BigDecimal("0.8")));
        }

        return totalFee;
    }

    @Override
    public String getFeeType() {
        return FeeType.ELECTRIC_FEE.getCode();
    }
}