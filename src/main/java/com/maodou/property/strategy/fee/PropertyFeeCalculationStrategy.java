package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 物业费计算策略
@Component
public class PropertyFeeCalculationStrategy implements FeeCalculationStrategy {

    @Autowired
    private SystemConfigService configService;

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 物业费 = 房屋面积 * 单价
        String propertyFeeUnitPrice = configService.getConfigValue("property_fee_unit_price");
        BigDecimal unitPrice = new BigDecimal(propertyFeeUnitPrice);
        return context.getRoom().getArea().multiply(unitPrice);
    }

    @Override
    public String getFeeType() {
        return FeeType.PROPERTY_FEE.getCode();
    }
}