package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 停车费计算策略
@Component
public class ParkingFeeCalculationStrategy implements FeeCalculationStrategy {

    @Autowired
    private SystemConfigService configService;

    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 停车费 = 停车位数量 * 月租费
        Integer parkingSpaces = context.getParkingSpaces();
        if (parkingSpaces == null || parkingSpaces <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyFee = configService.getDecimalConfig("parking_fee_monthly");
        return monthlyFee.multiply(new BigDecimal(parkingSpaces));
    }
    
    @Override
    public String getFeeType() {
        return FeeType.PARKING_FEE.getCode();
    }
}