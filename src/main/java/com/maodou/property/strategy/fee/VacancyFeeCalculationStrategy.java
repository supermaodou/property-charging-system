package com.maodou.property.strategy.fee;

import com.maodou.property.entity.Room;
import com.maodou.property.enums.FeeType;
import com.maodou.property.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// 房屋空置费计算策略
@Component
public class VacancyFeeCalculationStrategy implements FeeCalculationStrategy {
    
    @Autowired
    private SystemConfigService configService;
    
    @Override
    public BigDecimal calculateFee(FeeCalculationContext context) {
        Room room = context.getRoom();
        
        // 如果房屋不是空置状态，则不收空置费
        Boolean isVacant = context.getIsVacant() != null ? context.getIsVacant() : room.getIsVacant();
        if (isVacant == null || !isVacant) {
            return BigDecimal.ZERO;
        }
        
        // 空置费 = 物业费 * 空置费率
        BigDecimal propertyFeeUnitPrice = configService.getDecimalConfig("property_fee_unit_price");
        BigDecimal vacancyFeeRate = configService.getDecimalConfig("vacancy_fee_rate");
        
        BigDecimal propertyFee = room.getArea().multiply(propertyFeeUnitPrice);
        return propertyFee.multiply(vacancyFeeRate);
    }
    
    @Override
    public String getFeeType() {
        return FeeType.VACANCY_FEE.getCode();
    }
}