package com.maodou.property.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 1. 枚举定义
// 费用类型枚举
@Getter
@AllArgsConstructor
public enum FeeType {
    PROPERTY_FEE("PROPERTY_FEE", "物业费"),
    WATER_FEE("WATER_FEE", "水费"),
    ELECTRIC_FEE("ELECTRIC_FEE", "电费"),
    GAS_FEE("GAS_FEE", "燃气费"),
    PARKING_FEE("PARKING_FEE", "停车费"),
    ELEVATOR_FEE("ELEVATOR_FEE", "电梯费"),
    GARBAGE_FEE("GARBAGE_FEE", "垃圾费"),
    VACANCY_FEE("VACANCY_FEE", "房屋空置费");
    
    private final String code;
    private final String description;
}