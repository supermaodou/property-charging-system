package com.maodou.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maodou.property.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}