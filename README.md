接口列表
```
// 1. 账单管理
GET  /api/bills/unpaid/room/{roomId}           // 获取房屋未缴费账单
GET  /api/bills/unpaid/owner/{ownerId}         // 获取业主未缴费账单
POST /api/bills/generate                       // 生成月度账单
POST /api/bills/pay                           // 创建支付订单
POST /api/bills/callback/{paymentMethod}      // 支付回调

// 2. 配置管理
GET  /api/config/{configKey}                  // 获取单个配置
POST /api/config/batch                        // 批量获取配置
PUT  /api/config/{configKey}                  // 更新配置
PUT  /api/config/batch                        // 批量更新配置
GET  /api/config/fees/all                     // 获取所有费用配置
POST /api/config/cache/clear                  // 清空缓存

// 3. 统计查询
GET  /api/statistics/monthly/{billMonth}       // 月度统计
GET  /api/statistics/room/{roomId}/history     // 房屋缴费历史
```

使用示例
```
// 1. 生成月度账单
POST /api/bills/generate
{
    "roomId": 1,
    "billMonth": "2024-01",
    "waterUsage": 25.5,
    "electricUsage": 350.0,
    "gasUsage": 80.0,
    "parkingSpaces": 1,
    "isVacant": false
}

// 2. 修改费用配置
PUT /api/config/batch
{
    "property_fee_unit_price": "3.0",
    "water_fee_tier1_price": "3.5",
    "elevator_fee_monthly_per_room": "35"
}

// 3. 查询业主未缴费账单
GET /api/bills/unpaid/owner/1

// 4. 创建支付订单
POST /api/bills/pay
{
    "billId": 1,
    "userId": 1,
    "paymentMethod": "ALIPAY",
    "notifyUrl": "https://your-domain.com/callback",
    "returnUrl": "https://your-domain.com/success"
}
```