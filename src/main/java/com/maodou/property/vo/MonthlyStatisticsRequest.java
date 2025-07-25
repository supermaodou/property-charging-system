package com.maodou.property.vo;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class MonthlyStatisticsRequest {
    private String billMonth;                      // 统计月份
    private BigDecimal totalAmount;               // 应收总金额
    private BigDecimal actualAmount;              // 实收总金额
    private BigDecimal collectionRate;            // 收缴率
    private Integer totalBills;                   // 总账单数
    private Integer paidBills;                    // 已缴费账单数
    private Map<String, BigDecimal> feeTypeStats; // 各费用类型统计
}