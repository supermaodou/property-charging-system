package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 水费计算策略
@Component
public class WaterFeeCalculationStrategy implements FeeCalculationStrategy {

    @Autowired
    private SystemConfigService configService;

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 阶梯水费计算
        BigDecimal usage = context.getWaterUsage();
        if (usage == null || usage.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal tier1Limit = configService.getDecimalConfig("water_fee_tier1_limit");
        BigDecimal tier2Limit = configService.getDecimalConfig("water_fee_tier2_limit");
        BigDecimal tier1Price = configService.getDecimalConfig("water_fee_tier1_price");
        BigDecimal tier2Price = configService.getDecimalConfig("water_fee_tier2_price");
        BigDecimal tier3Price = configService.getDecimalConfig("water_fee_tier3_price");

        BigDecimal totalFee = BigDecimal.ZERO;

        if (usage.compareTo(tier1Limit) <= 0) {
            // 第一阶梯
            totalFee = usage.multiply(tier1Price);
        } else if (usage.compareTo(tier2Limit) <= 0) {
            // 第二阶梯
            totalFee = tier1Limit.multiply(tier1Price)
                    .add(usage.subtract(tier1Limit).multiply(tier2Price));
        } else {
            // 第三阶梯
            totalFee = tier1Limit.multiply(tier1Price)
                    .add(tier2Limit.subtract(tier1Limit).multiply(tier2Price))
                    .add(usage.subtract(tier2Limit).multiply(tier3Price));
        }

        return totalFee;
    }

    @Override
    public String getFeeType() {
        return FeeType.WATER_FEE.getCode();
    }
}