package com.maodou.property.controller;

import com.maodou.property.dto.CreatePaymentRequest;
import com.maodou.property.dto.GenerateBillRequest;
import com.maodou.property.dto.PaymentRequest;
import com.maodou.property.entity.Bill;
import com.maodou.property.service.BillService;
import com.maodou.property.service.PaymentService;
import com.maodou.property.util.Result;
import com.maodou.property.vo.PaymentResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 8. 控制器
@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 获取房屋未缴费账单
     */
    @GetMapping("/unpaid/room/{roomId}")
    public Result<List<Bill>> getUnpaidBillsByRoomId(@PathVariable Long roomId) {
        List<Bill> bills = billService.getUnpaidBillsByRoomId(roomId);
        return Result.success(bills);
    }

    /**
     * 获取业主未缴费账单
     */
    @GetMapping("/unpaid/owner/{ownerId}")
    public Result<List<Bill>> getUnpaidBillsByOwnerId(@PathVariable Long ownerId) {
        List<Bill> bills = billService.getUnpaidBillsByOwnerId(ownerId);
        return Result.success(bills);
    }

    /**
     * 生成月度账单
     */
    @PostMapping("/generate")
    public Result<String> generateMonthlyBills(@RequestBody GenerateBillRequest request) {
        billService.generateMonthlyBills(
                request.getRoomId(),
                request.getBillMonth(),
                request.getWaterUsage(),
                request.getElectricUsage(),
                request.getGasUsage(),
                request.getParkingSpaces()
        );
        return Result.success("账单生成成功");
    }

    /**
     * 创建支付订单
     */
    @PostMapping("/pay")
    public Result<PaymentResult> createPayment(@RequestBody CreatePaymentRequest request) {
        PaymentResult result = paymentService.createPayment(
                request.getBillId(),
                request.getUserId(),
                request.getPaymentMethod(),
                request.getNotifyUrl(),
                request.getReturnUrl()
        );
        return Result.success(result);
    }

    /**
     * 支付回调接口
     */
    @PostMapping("/callback/{paymentMethod}")
    public Result<String> paymentCallback(@PathVariable String paymentMethod,
                                          @RequestBody Map<String, Object> callbackData) {
        // 这里需要根据不同支付平台的回调格式进行处理
        String transactionId = (String) callbackData.get("transactionId");
        boolean success = (Boolean) callbackData.get("success");

        paymentService.handlePaymentCallback(transactionId, success);
        return Result.success("回调处理成功");
    }
}