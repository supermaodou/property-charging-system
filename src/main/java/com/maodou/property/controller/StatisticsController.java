package com.maodou.property.controller;

import com.maodou.property.service.StatisticsService;
import com.maodou.property.util.Result;
import com.maodou.property.vo.BillHistoryRequest;
import com.maodou.property.vo.MonthlyStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 统计控制器
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取月度统计数据
     */
    @GetMapping("/monthly/{billMonth}")
    public Result<MonthlyStatisticsRequest> getMonthlyStatistics(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") String billMonth) {
        MonthlyStatisticsRequest statistics = statisticsService.getMonthlyStatistics(billMonth);
        return Result.success(statistics);
    }

    /**
     * 获取房屋缴费历史记录
     */
    @GetMapping("/room/{roomId}/history")
    public Result<List<BillHistoryRequest>> getRoomBillHistory(@PathVariable Long roomId) {
        List<BillHistoryRequest> history = statisticsService.getRoomBillHistory(roomId);
        return Result.success(history);
    }
}