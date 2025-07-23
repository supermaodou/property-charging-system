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
    "parkingSpaces": 1
}

// 2. 查询业主未缴费账单
GET /api/bills/unpaid/owner/1

// 3. 创建支付订单
POST /api/bills/pay
{
    "billId": 1,
    "userId": 1,
    "paymentMethod": "ALIPAY",
    "notifyUrl": "https://your-domain.com/callback",
    "returnUrl": "https://your-domain.com/success"
}
```