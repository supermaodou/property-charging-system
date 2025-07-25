package com.maodou.property.strategy.fee;

import com.maodou.property.entity.Room;
import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 垃圾费计算策略
@Component
public class GarbageFeeCalculationStrategy implements FeeCalculationStrategy {
    
    @Autowired
    private SystemConfigService configService;
    
    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        Room room = context.getRoom();
        
        // 垃圾费计算方式：按面积收费或按人数收费
        String calculationMethod = configService.getConfigValue("garbage_fee_calculation_method");
        
        if ("BY_AREA".equals(calculationMethod)) {
            // 按面积计费
            BigDecimal unitPrice = configService.getDecimalConfig("garbage_fee_unit_price_area");
            return room.getArea().multiply(unitPrice);
        } else if ("BY_PERSON".equals(calculationMethod)) {
            // 按人数计费
            BigDecimal unitPrice = configService.getDecimalConfig("garbage_fee_unit_price_person");
            Integer residentCount = room.getResidentCount() != null ? room.getResidentCount() : 1;
            return unitPrice.multiply(new BigDecimal(residentCount));
        } else {
            // 按户计费：固定金额
            return configService.getDecimalConfig("garbage_fee_monthly_per_room");
        }
    }
    
    @Override
    public String getFeeType() {
        return FeeType.GARBAGE_FEE.getCode();
    }
}