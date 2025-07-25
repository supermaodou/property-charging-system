package com.maodou.property.strategy.fee;

import com.maodou.property.entity.Room;
import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 电梯费计算策略
@Component
public class ElevatorFeeCalculationStrategy implements FeeCalculationStrategy {
    
    @Autowired
    private SystemConfigService configService;
    
    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        Room room = context.getRoom();
        
        // 如果房屋没有电梯，则不收费
        if (room.getHasElevator() == null || !room.getHasElevator()) {
            return BigDecimal.ZERO;
        }
        
        // 电梯费计算方式：按面积收费或按户收费
        String calculationMethod = configService.getConfigValue("elevator_fee_calculation_method");
        
        if ("BY_AREA".equals(calculationMethod)) {
            // 按面积计费：面积 * 单价
            BigDecimal unitPrice = configService.getDecimalConfig("elevator_fee_unit_price_area");
            return room.getArea().multiply(unitPrice);
        } else {
            // 按户计费：固定金额
            return configService.getDecimalConfig("elevator_fee_monthly_per_room");
        }
    }
    
    @Override
    public String getFeeType() {
        return FeeType.ELEVATOR_FEE.getCode();
    }
}