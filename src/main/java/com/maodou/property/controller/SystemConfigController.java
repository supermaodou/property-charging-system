package com.maodou.property.controller;

import com.maodou.property.dto.UpdateConfigRequest;
import com.maodou.property.service.SystemConfigService;
import com.maodou.property.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 配置管理控制器
@RestController
@RequestMapping("/api/config")
public class SystemConfigController {
    
    @Autowired
    private SystemConfigService configService;
    
    /**
     * 获取配置值
     */
    @GetMapping("/{configKey}")
    public Result<String> getConfig(@PathVariable String configKey) {
        String value = configService.getConfigValue(configKey);
        return Result.success(value);
    }
    
    /**
     * 批量获取配置
     */
    @PostMapping("/batch")
    public Result<Map<String, String>> getBatchConfig(@RequestBody List<String> configKeys) {
        Map<String, String> configs = new HashMap<>();
        for (String key : configKeys) {
            configs.put(key, configService.getConfigValue(key));
        }
        return Result.success(configs);
    }
    
    /**
     * 更新配置值
     */
    @PutMapping("/{configKey}")
    public Result<String> updateConfig(@PathVariable String configKey, @RequestBody UpdateConfigRequest request) {
        configService.updateConfig(configKey, request.getConfigValue());
        return Result.success("配置更新成功");
    }
    
    /**
     * 批量更新配置
     */
    @PutMapping("/batch")
    public Result<String> updateBatchConfig(@RequestBody Map<String, String> configs) {
        for (Map.Entry<String, String> entry : configs.entrySet()) {
            configService.updateConfig(entry.getKey(), entry.getValue());
        }
        return Result.success("批量配置更新成功");
    }
    
    /**
     * 清空配置缓存
     */
    @PostMapping("/cache/clear")
    public Result<String> clearCache() {
        configService.clearCache();
        return Result.success("缓存清空成功");
    }
    
    /**
     * 获取所有费用配置
     */
    @GetMapping("/fees/all")
    public Result<Map<String, Object>> getAllFeeConfigs() {
        Map<String, Object> feeConfigs = new HashMap<>();
        
        // 物业费配置
        Map<String, Object> propertyFee = new HashMap<>();
        propertyFee.put("unitPrice", configService.getDecimalConfig("property_fee_unit_price"));
        feeConfigs.put("propertyFee", propertyFee);
        
        // 水费配置
        Map<String, Object> waterFee = new HashMap<>();
        waterFee.put("tier1Price", configService.getDecimalConfig("water_fee_tier1_price"));
        waterFee.put("tier1Limit", configService.getDecimalConfig("water_fee_tier1_limit"));
        waterFee.put("tier2Price", configService.getDecimalConfig("water_fee_tier2_price"));
        waterFee.put("tier2Limit", configService.getDecimalConfig("water_fee_tier2_limit"));
        waterFee.put("tier3Price", configService.getDecimalConfig("water_fee_tier3_price"));
        feeConfigs.put("waterFee", waterFee);
        
        // 电费配置
        Map<String, Object> electricFee = new HashMap<>();
        electricFee.put("tier1Price", configService.getDecimalConfig("electric_fee_tier1_price"));
        electricFee.put("tier1Limit", configService.getDecimalConfig("electric_fee_tier1_limit"));
        electricFee.put("tier2Price", configService.getDecimalConfig("electric_fee_tier2_price"));
        electricFee.put("tier2Limit", configService.getDecimalConfig("electric_fee_tier2_limit"));
        electricFee.put("tier3Price", configService.getDecimalConfig("electric_fee_tier3_price"));
        feeConfigs.put("electricFee", electricFee);
        
        // 燃气费配置
        Map<String, Object> gasFee = new HashMap<>();
        gasFee.put("tier1Price", configService.getDecimalConfig("gas_fee_tier1_price"));
        gasFee.put("tier1Limit", configService.getDecimalConfig("gas_fee_tier1_limit"));
        gasFee.put("tier2Price", configService.getDecimalConfig("gas_fee_tier2_price"));
        gasFee.put("tier2Limit", configService.getDecimalConfig("gas_fee_tier2_limit"));
        gasFee.put("tier3Price", configService.getDecimalConfig("gas_fee_tier3_price"));
        feeConfigs.put("gasFee", gasFee);
        
        // 停车费配置
        Map<String, Object> parkingFee = new HashMap<>();
        parkingFee.put("monthlyFee", configService.getDecimalConfig("parking_fee_monthly"));
        feeConfigs.put("parkingFee", parkingFee);
        
        // 电梯费配置
        Map<String, Object> elevatorFee = new HashMap<>();
        elevatorFee.put("calculationMethod", configService.getConfigValue("elevator_fee_calculation_method"));
        elevatorFee.put("unitPriceArea", configService.getDecimalConfig("elevator_fee_unit_price_area"));
        elevatorFee.put("monthlyPerRoom", configService.getDecimalConfig("elevator_fee_monthly_per_room"));
        feeConfigs.put("elevatorFee", elevatorFee);
        
        // 垃圾费配置
        Map<String, Object> garbageFee = new HashMap<>();
        garbageFee.put("calculationMethod", configService.getConfigValue("garbage_fee_calculation_method"));
        garbageFee.put("unitPriceArea", configService.getDecimalConfig("garbage_fee_unit_price_area"));
        garbageFee.put("unitPricePerson", configService.getDecimalConfig("garbage_fee_unit_price_person"));
        garbageFee.put("monthlyPerRoom", configService.getDecimalConfig("garbage_fee_monthly_per_room"));
        feeConfigs.put("garbageFee", garbageFee);
        
        // 房屋空置费配置
        Map<String, Object> vacancyFee = new HashMap<>();
        vacancyFee.put("rate", configService.getDecimalConfig("vacancy_fee_rate"));
        feeConfigs.put("vacancyFee", vacancyFee);
        
        return Result.success(feeConfigs);
    }
}