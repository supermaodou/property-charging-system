package com.maodou.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maodou.property.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {

    @Select("SELECT * FROM payments WHERE bill_id = #{billId}")
    List<Payment> findPaymentsByBillId(@Param("billId") Long billId);

    @Select("SELECT * FROM payments WHERE room_id = #{roomId} AND status = #{status}")
    List<Payment> findPaymentsByRoomIdAndStatus(@Param("roomId") Long roomId, @Param("status") Integer status);
}