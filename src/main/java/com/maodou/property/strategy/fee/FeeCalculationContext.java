package com.maodou.property.strategy.fee;

import com.maodou.property.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

// 费用计算上下文
@Data
@Builder
public class FeeCalculationContext {
    private Long roomId;
    private Room room; // 房屋信息
    private String billMonth;
    private BigDecimal waterUsage; // 用水量
    private BigDecimal electricUsage; // 用电量
    private BigDecimal gasUsage; // 燃气用量
    private Integer parkingSpaces; // 停车位数量
    private Map<String, Object> additionalParams;
}