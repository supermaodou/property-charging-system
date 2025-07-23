package com.maodou.property.strategy.fee;

import com.maodou.property.enums.FeeType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 停车费计算策略
@Component
public class ParkingFeeCalculationStrategy implements FeeCalculationStrategy {
    
    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        // 停车费 = 停车位数量 * 月租费(假设200元/月/位)
        Integer parkingSpaces = context.getParkingSpaces();
        if (parkingSpaces == null || parkingSpaces <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal monthlyFee = new BigDecimal("200");
        return monthlyFee.multiply(new BigDecimal(parkingSpaces));
    }
    
    @Override
    public String getFeeType() {
        return FeeType.PARKING_FEE.getCode();
    }
}