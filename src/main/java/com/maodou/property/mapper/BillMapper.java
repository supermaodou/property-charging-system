package com.maodou.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maodou.property.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 6. Mapper接口
@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    @Select("SELECT * FROM bills WHERE room_id = #{roomId} AND status = #{status}")
    List<Bill> findBillsByRoomIdAndStatus(@Param("roomId") Long roomId, @Param("status") Integer status);

    @Select("SELECT * FROM bills WHERE room_id = #{roomId} AND bill_month = #{billMonth}")
    List<Bill> findBillsByRoomIdAndMonth(@Param("roomId") Long roomId, @Param("billMonth") String billMonth);

    @Select("SELECT b.*, r.room_number, r.building_number FROM bills b " +
            "LEFT JOIN rooms r ON b.room_id = r.id " +
            "WHERE r.owner_id = #{ownerId} AND b.status = #{status}")
    List<Bill> findBillsByOwnerIdAndStatus(@Param("ownerId") Long ownerId, @Param("status") Integer status);
}