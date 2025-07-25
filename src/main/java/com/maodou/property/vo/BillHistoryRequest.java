package com.maodou.property.vo;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class BillHistoryRequest {
    private Long billId;           // 账单ID
    private String billMonth;      // 账单月份
    private BigDecimal amount;     // 账单金额
    private Integer status;         // 账单状态
    private LocalDateTime payTime; // 支付时间
    private String payMethod;      // 支付方式
    private Map<String, BigDecimal> feeDetails; // 费用明细
}