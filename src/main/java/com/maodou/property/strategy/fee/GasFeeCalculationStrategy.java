package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 燃气费计算策略
@Component
public class GasFeeCalculationStrategy implements FeeCalculationStrategy {
    
    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 燃气费计算 - 阶梯计费
        BigDecimal usage = context.getGasUsage();
        BigDecimal totalFee = BigDecimal.ZERO;
        
        if (usage.compareTo(new BigDecimal("100")) <= 0) {
            // 100立方以内，2.5元/立方
            totalFee = usage.multiply(new BigDecimal("2.5"));
        } else if (usage.compareTo(new BigDecimal("200")) <= 0) {
            // 100-200立方，3.0元/立方
            totalFee = new BigDecimal("100").multiply(new BigDecimal("2.5"))
                    .add(usage.subtract(new BigDecimal("100")).multiply(new BigDecimal("3.0")));
        } else {
            // 200立方以上，3.5元/立方
            totalFee = new BigDecimal("250") // 前100立方
                    .add(new BigDecimal("300")) // 100-200立方
                    .add(usage.subtract(new BigDecimal("200")).multiply(new BigDecimal("3.5")));
        }
        
        return totalFee;
    }
    
    @Override
    public String getFeeType() {
        return FeeType.GAS_FEE.getCode();
    }
}