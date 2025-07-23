package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 物业费计算策略
@Component
public class PropertyFeeCalculationStrategy implements FeeCalculationStrategy {

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 物业费 = 房屋面积 * 单价(假设2.5元/㎡)
        BigDecimal unitPrice = new BigDecimal("2.5");
        return context.getRoom().getArea().multiply(unitPrice);
    }

    @Override
    public String getFeeType() {
        return FeeType.PROPERTY_FEE.getCode();
    }
}