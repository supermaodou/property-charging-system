package com.maodou.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maodou.property.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// 系统配置Mapper
@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfig> {
    
    @Select("SELECT * FROM system_config WHERE config_key = #{configKey}")
    SystemConfig selectByKey(@Param("configKey") String configKey);
}