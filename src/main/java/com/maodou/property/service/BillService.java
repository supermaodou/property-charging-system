package com.maodou.property.service;

import com.maodou.property.entity.Bill;
import com.maodou.property.entity.Room;
import com.maodou.property.enums.BillStatus;
import com.maodou.property.enums.FeeType;
import com.maodou.property.mapper.BillMapper;
import com.maodou.property.mapper.RoomMapper;
import com.maodou.property.strategy.fee.FeeCalculationContext;
import com.maodou.property.strategy.fee.FeeCalculationStrategy;
import com.maodou.property.strategy.fee.FeeCalculationStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// 7. 服务层
@Service
@Transactional
public class BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private FeeCalculationStrategyFactory feeCalculationFactory;

    /**
     * 生成账单
     */
    public Bill generateBill(Long roomId, FeeType feeType, String billMonth, FeeCalculationContext context) {
        FeeCalculationStrategy strategy = feeCalculationFactory.getStrategy(feeType.getCode());
        BigDecimal amount = strategy.calculateFee(context);

        Bill bill = new Bill();
        bill.setRoomId(roomId);
        bill.setBillType(feeType.getCode());
        bill.setAmount(amount);
        bill.setBillMonth(billMonth);
        bill.setStatus(BillStatus.UNPAID.getCode());
        bill.setDueDate(LocalDateTime.now().plusDays(30)); // 30天后到期
        bill.setCreateTime(LocalDateTime.now());
        bill.setUpdateTime(LocalDateTime.now());

        // 设置用量和单价信息
        switch (feeType) {
            case WATER_FEE:
                bill.setUsageAmount(context.getWaterUsage());
                break;
            case ELECTRIC_FEE:
                bill.setUsageAmount(context.getElectricUsage());
                break;
            case GAS_FEE:
                bill.setUsageAmount(context.getGasUsage());
                break;
            case PROPERTY_FEE:
                bill.setUsageAmount(context.getRoom().getArea());
                bill.setUnitPrice(new BigDecimal("2.5"));
                break;
            case PARKING_FEE:
                bill.setUsageAmount(new BigDecimal(context.getParkingSpaces()));
                bill.setUnitPrice(new BigDecimal("200"));
                break;
        }

        billMapper.insert(bill);
        return bill;
    }

    /**
     * 获取房屋未缴费账单
     */
    public List<Bill> getUnpaidBillsByRoomId(Long roomId) {
        return billMapper.findBillsByRoomIdAndStatus(roomId, BillStatus.UNPAID.getCode());
    }

    /**
     * 获取业主未缴费账单
     */
    public List<Bill> getUnpaidBillsByOwnerId(Long ownerId) {
        return billMapper.findBillsByOwnerIdAndStatus(ownerId, BillStatus.UNPAID.getCode());
    }

    /**
     * 批量生成月度账单
     */
    public void generateMonthlyBills(Long roomId, String billMonth,
                                     BigDecimal waterUsage, BigDecimal electricUsage, BigDecimal gasUsage, Integer parkingSpaces) {

        Room room = roomMapper.selectById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("房屋不存在");
        }

        FeeCalculationContext context = FeeCalculationContext.builder()
                .roomId(roomId)
                .room(room)
                .billMonth(billMonth)
                .waterUsage(waterUsage)
                .electricUsage(electricUsage)
                .gasUsage(gasUsage)
                .parkingSpaces(parkingSpaces)
                .build();

        // 生成各类费用账单
        generateBill(roomId, FeeType.PROPERTY_FEE, billMonth, context);

        if (waterUsage != null && waterUsage.compareTo(BigDecimal.ZERO) > 0) {
            generateBill(roomId, FeeType.WATER_FEE, billMonth, context);
        }

        if (electricUsage != null && electricUsage.compareTo(BigDecimal.ZERO) > 0) {
            generateBill(roomId, FeeType.ELECTRIC_FEE, billMonth, context);
        }

        if (gasUsage != null && gasUsage.compareTo(BigDecimal.ZERO) > 0) {
            generateBill(roomId, FeeType.GAS_FEE, billMonth, context);
        }

        if (parkingSpaces != null && parkingSpaces > 0) {
            generateBill(roomId, FeeType.PARKING_FEE, billMonth, context);
        }
    }
}