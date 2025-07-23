package com.maodou.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maodou.property.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 6. Mapper接口
@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    
    @Select("SELECT * FROM rooms WHERE owner_id = #{ownerId}")
    List<Room> findRoomsByOwnerId(@Param("ownerId") Long ownerId);
    
    @Select("SELECT * FROM rooms WHERE room_number = #{roomNumber}")
    Room findByRoomNumber(@Param("roomNumber") String roomNumber);
}