package com.maodou.property.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maodou.property.entity.Bill;
import com.maodou.property.enums.BillStatus;
import com.maodou.property.mapper.BillMapper;
import com.maodou.property.vo.BillHistoryRequest;
import com.maodou.property.vo.MonthlyStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private BillMapper billMapper;

    /**
     * 获取月度统计数据
     */
    public MonthlyStatisticsRequest getMonthlyStatistics(String billMonth) {
        // 构建查询条件
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getBillMonth, billMonth);
        List<Bill> monthBills = billMapper.selectList(wrapper);

        // 计算总账单数和已缴费账单数
        int totalBills = monthBills.size();
        int paidBills = (int) monthBills.stream()
                .filter(bill -> BillStatus.PAID.getCode().equals(bill.getStatus()))
                .count();

        // 计算应收总金额和实收总金额
        BigDecimal totalAmount = monthBills.stream()
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal actualAmount = monthBills.stream()
                .filter(bill -> BillStatus.PAID.getCode().equals(bill.getStatus()))
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算收缴率
        BigDecimal collectionRate = totalAmount.compareTo(BigDecimal.ZERO) > 0
                ? actualAmount.divide(totalAmount, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                : BigDecimal.ZERO;

        // 按费用类型统计金额
        Map<String, BigDecimal> feeTypeStats = monthBills.stream()
                .collect(Collectors.groupingBy(
                        Bill::getBillType,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Bill::getAmount,
                                BigDecimal::add
                        )
                ));

        return MonthlyStatisticsRequest.builder()
                .billMonth(billMonth)
                .totalAmount(totalAmount)
                .actualAmount(actualAmount)
                .collectionRate(collectionRate)
                .totalBills(totalBills)
                .paidBills(paidBills)
                .feeTypeStats(feeTypeStats)
                .build();
    }

    /**
     * 获取房屋缴费历史记录
     */
    public List<BillHistoryRequest> getRoomBillHistory(Long roomId) {
        // 获取指定房屋的所有账单记录
        List<Bill> bills = billMapper.selectByRoomId(roomId);

        // 转换为VO对象
        return bills.stream()
                .map(bill -> BillHistoryRequest.builder()
                        .billId(bill.getId())
                        .billMonth(bill.getBillMonth())
                        .amount(bill.getAmount())
                        .status(BillStatus.PAID.getCode())
                        .payTime(bill.getPayTime())
//                        .payMethod(bill.getPayMethod())
//                        .feeDetails(bill.getFeeDetails())
                        .build())
                .collect(Collectors.toList());
    }
}