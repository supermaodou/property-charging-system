package com.maodou.property.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maodou.property.entity.Bill;
import com.maodou.property.entity.Payment;
import com.maodou.property.enums.BillStatus;
import com.maodou.property.enums.PaymentStatus;
import com.maodou.property.mapper.BillMapper;
import com.maodou.property.mapper.PaymentMapper;
import com.maodou.property.strategy.payment.PaymentStrategy;
import com.maodou.property.strategy.payment.PaymentStrategyFactory;
import com.maodou.property.vo.PaymentRequest;
import com.maodou.property.vo.PaymentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PaymentStrategyFactory paymentStrategyFactory;

    /**
     * 创建支付订单
     */
    public PaymentResult createPayment(Long billId, Long userId, String paymentMethod, String notifyUrl, String returnUrl) {
        Bill bill = billMapper.selectById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("账单不存在");
        }

        if (bill.getStatus().equals(BillStatus.PAID.getCode())) {
            throw new IllegalStateException("账单已缴费");
        }

        // 创建支付记录
        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setRoomId(bill.getRoomId());
        payment.setUserId(userId);
        payment.setAmount(bill.getAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING.getCode());
        payment.setCreateTime(LocalDateTime.now());
        payment.setUpdateTime(LocalDateTime.now());
        paymentMapper.insert(payment);

        // 调用支付策略
        PaymentStrategy strategy = paymentStrategyFactory.getStrategy(paymentMethod);
        PaymentRequest request = PaymentRequest.builder()
                .billId(billId)
                .userId(userId)
                .amount(bill.getAmount())
                .notifyUrl(notifyUrl)
                .returnUrl(returnUrl)
                .build();

        PaymentResult result = strategy.processPayment(request);

        if (result.isSuccess()) {
            // 更新支付记录
            payment.setTransactionId(result.getTransactionId());
            paymentMapper.updateById(payment);
        }

        return result;
    }

    /**
     * 处理支付回调
     */
    public void handlePaymentCallback(String transactionId, boolean success) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getTransactionId, transactionId);
        Payment payment = paymentMapper.selectOne(wrapper);

        if (payment != null) {
            payment.setStatus(success ? PaymentStatus.SUCCESS.getCode() : PaymentStatus.FAILED.getCode());
            payment.setPayTime(LocalDateTime.now());
            payment.setUpdateTime(LocalDateTime.now());
            paymentMapper.updateById(payment);

            if (success) {
                // 更新账单状态
                Bill bill = billMapper.selectById(payment.getBillId());
                bill.setStatus(BillStatus.PAID.getCode());
                bill.setPayTime(LocalDateTime.now());
                bill.setUpdateTime(LocalDateTime.now());
                billMapper.updateById(bill);
            }
        }
    }
}