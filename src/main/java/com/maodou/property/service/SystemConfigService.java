package com.maodou.property.service;

import com.maodou.property.entity.SystemConfig;
import com.maodou.property.mapper.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 系统配置服务
@Service
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper configMapper;

    private final Map<String, String> configCache = new ConcurrentHashMap<>();

    /**
     * 获取配置值
     */
    public String getConfigValue(String key) {
        return configCache.computeIfAbsent(key, k -> {
            SystemConfig config = configMapper.selectByKey(k);
            return config != null ? config.getConfigValue() : null;
        });
    }

    /**
     * 获取数字配置值
     */
    public BigDecimal getDecimalConfig(String key) {
        String value = getConfigValue(key);
        return value != null ? new BigDecimal(value) : BigDecimal.ZERO;
    }

    /**
     * 获取整数配置值
     */
    public Integer getIntConfig(String key) {
        String value = getConfigValue(key);
        return value != null ? Integer.valueOf(value) : 0;
    }

    /**
     * 获取布尔配置值
     */
    public Boolean getBooleanConfig(String key) {
        String value = getConfigValue(key);
        return value != null ? Boolean.valueOf(value) : false;
    }

    /**
     * 更新配置并刷新缓存
     */
    public void updateConfig(String key, String value) {
        SystemConfig config = configMapper.selectByKey(key);
        if (config != null) {
            config.setConfigValue(value);
            config.setUpdateTime(LocalDateTime.now());
            configMapper.updateById(config);
            configCache.put(key, value);
        }
    }

    /**
     * 清空配置缓存
     */
    public void clearCache() {
        configCache.clear();
    }
}