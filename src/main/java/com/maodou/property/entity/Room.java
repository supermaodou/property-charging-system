package com.maodou.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// 房屋实体
@Data
@TableName("rooms")
public class Room {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("room_number")
    private String roomNumber;
    
    @TableField("building_number")
    private String buildingNumber;
    
    @TableField("unit_number")
    private String unitNumber;
    
    @TableField("floor")
    private Integer floor;
    
    @TableField("area")
    private BigDecimal area; // 房屋面积
    
    @TableField("owner_id")
    private Long ownerId; // 业主用户ID
    
    @TableField("resident_count")
    private Integer residentCount; // 居住人数
    
    @TableField("room_type")
    private String roomType; // 房屋类型：住宅、商铺等

    @TableField("is_vacant")
    private Boolean isVacant; // 是否空置房屋

    @TableField("has_elevator")
    private Boolean hasElevator; // 是否有电梯
    
    @TableField("create_time")
    private LocalDateTime createTime;
    
    @TableField("update_time")
    private LocalDateTime updateTime;
}