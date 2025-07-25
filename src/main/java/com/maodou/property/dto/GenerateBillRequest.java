package com.maodou.property.dto;

import lombok.Data;

import java.math.BigDecimal;

// 9. 请求/响应DTO
@Data
public class GenerateBillRequest {
    private Long roomId;
    private String billMonth;
    private BigDecimal waterUsage;
    private BigDecimal electricUsage;
    private BigDecimal gasUsage;
    private Integer parkingSpaces;
    private Boolean isVacant; // 是否空置
}