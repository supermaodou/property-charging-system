-- 物业缴费系统数据库建表SQL

-- 用户表
CREATE TABLE `users`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(50) NOT NULL COMMENT '用户名',
    `phone`       VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `id_card`     VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `create_time` DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_username` (`username`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户表';

-- 房屋表
CREATE TABLE `rooms`
(
    `id`              BIGINT         NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
    `room_number`     VARCHAR(20)    NOT NULL COMMENT '房间号',
    `building_number` VARCHAR(10)    NOT NULL COMMENT '楼栋号',
    `unit_number`     VARCHAR(10) DEFAULT NULL COMMENT '单元号',
    `floor`           INT         DEFAULT NULL COMMENT '楼层',
    `area`            DECIMAL(10, 2) NOT NULL COMMENT '房屋面积(平方米)',
    `owner_id`        BIGINT         NOT NULL COMMENT '业主用户ID',
    `resident_count`  INT         DEFAULT 1 COMMENT '居住人数',
    `room_type`       VARCHAR(20) DEFAULT 'RESIDENTIAL' COMMENT '房屋类型(RESIDENTIAL-住宅,COMMERCIAL-商铺,OFFICE-办公)',
    `is_vacant`       BOOLEAN     DEFAULT FALSE COMMENT '是否空置房屋',
    `has_elevator`    BOOLEAN     DEFAULT TRUE COMMENT '是否有电梯',
    `create_time`     DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_room_number` (`room_number`),
    KEY `idx_owner_id` (`owner_id`),
    KEY `idx_building_unit` (`building_number`, `unit_number`),
    KEY `idx_is_vacant` (`is_vacant`),
    KEY `idx_has_elevator` (`has_elevator`),
    CONSTRAINT `fk_rooms_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='房屋表';

-- 账单表
CREATE TABLE `bills`
(
    `id`           BIGINT         NOT NULL AUTO_INCREMENT COMMENT '账单ID',
    `room_id`      BIGINT         NOT NULL COMMENT '房屋ID',
    `bill_type`    VARCHAR(20)    NOT NULL COMMENT '账单类型(PROPERTY_FEE-物业费,WATER_FEE-水费,ELECTRIC_FEE-电费,GAS_FEE-燃气费,PARKING_FEE-停车费,ELEVATOR_FEE-电梯费,GARBAGE_FEE-垃圾费,VACANCY_FEE-房屋空置费)',
    `amount`       DECIMAL(10, 2) NOT NULL COMMENT '账单金额',
    `bill_month`   VARCHAR(7)     NOT NULL COMMENT '账单月份(格式:yyyy-MM)',
    `status`       INT            DEFAULT 0 COMMENT '账单状态(0-未缴费,1-已缴费,2-已逾期,3-已取消)',
    `due_date`     DATETIME       NOT NULL COMMENT '到期时间',
    `usage_amount` DECIMAL(10, 2) DEFAULT NULL COMMENT '用量(水电气等的使用量)',
    `unit_price`   DECIMAL(10, 4) DEFAULT NULL COMMENT '单价',
    `remark`       VARCHAR(255)   DEFAULT NULL COMMENT '备注',
    `create_time`  DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `pay_time`     DATETIME       DEFAULT NULL COMMENT '缴费时间',
    `update_time`  DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_room_type_month` (`room_id`, `bill_type`, `bill_month`),
    KEY `idx_room_id` (`room_id`),
    KEY `idx_bill_month` (`bill_month`),
    KEY `idx_status` (`status`),
    KEY `idx_due_date` (`due_date`),
    KEY `idx_bill_type` (`bill_type`),
    CONSTRAINT `fk_bills_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='费用账单表';

-- 支付记录表
CREATE TABLE `payments`
(
    `id`             BIGINT         NOT NULL AUTO_INCREMENT COMMENT '支付记录ID',
    `bill_id`        BIGINT         NOT NULL COMMENT '账单ID',
    `room_id`        BIGINT         NOT NULL COMMENT '房屋ID',
    `user_id`        BIGINT         NOT NULL COMMENT '支付用户ID',
    `amount`         DECIMAL(10, 2) NOT NULL COMMENT '支付金额',
    `payment_method` VARCHAR(20)    NOT NULL COMMENT '支付方式(ALIPAY-支付宝,WECHAT-微信支付,BANK_CARD-银行卡,UNION_PAY-银联支付)',
    `transaction_id` VARCHAR(100) DEFAULT NULL COMMENT '第三方交易流水号',
    `status`         INT          DEFAULT 0 COMMENT '支付状态(0-待支付,1-支付成功,2-支付失败,3-已取消)',
    `remark`         VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `pay_time`       DATETIME     DEFAULT NULL COMMENT '支付完成时间',
    `update_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_transaction_id` (`transaction_id`),
    KEY `idx_bill_id` (`bill_id`),
    KEY `idx_room_id` (`room_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_payment_method` (`payment_method`),
    KEY `idx_pay_time` (`pay_time`),
    CONSTRAINT `fk_payments_bill` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`),
    CONSTRAINT `fk_payments_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
    CONSTRAINT `fk_payments_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='支付记录表';

-- 用量记录表(可选，用于记录每月的水电气用量历史)
CREATE TABLE `usage_records`
(
    `id`               BIGINT         NOT NULL AUTO_INCREMENT COMMENT '用量记录ID',
    `room_id`          BIGINT         NOT NULL COMMENT '房屋ID',
    `usage_type`       VARCHAR(20)    NOT NULL COMMENT '用量类型(WATER-水,ELECTRIC-电,GAS-燃气)',
    `usage_month`      VARCHAR(7)     NOT NULL COMMENT '用量月份(格式:yyyy-MM)',
    `previous_reading` DECIMAL(10, 2) DEFAULT NULL COMMENT '上月读数',
    `current_reading`  DECIMAL(10, 2) NOT NULL COMMENT '当月读数',
    `usage_amount`     DECIMAL(10, 2) NOT NULL COMMENT '用量',
    `unit_price`       DECIMAL(10, 4) DEFAULT NULL COMMENT '单价',
    `remark`           VARCHAR(255)   DEFAULT NULL COMMENT '备注',
    `create_time`      DATETIME       DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_room_type_month` (`room_id`, `usage_type`, `usage_month`),
    KEY `idx_room_id` (`room_id`),
    KEY `idx_usage_month` (`usage_month`),
    CONSTRAINT `fk_usage_records_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用量记录表';

-- 系统配置表(用于存储各种费用的计算参数)
CREATE TABLE `system_config`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `config_key`   VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT         NOT NULL COMMENT '配置值',
    `config_type`  VARCHAR(20)  DEFAULT 'STRING' COMMENT '配置类型(STRING,NUMBER,JSON)',
    `description`  VARCHAR(255) DEFAULT NULL COMMENT '配置描述',
    `create_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统配置表';

-- 插入一些默认配置数据
INSERT INTO `system_config` (`config_key`, `config_value`, `config_type`, `description`)
VALUES
-- 物业费配置
('property_fee_unit_price', '2.5', 'NUMBER', '物业费单价(元/平方米)'),

-- 水费配置
('water_fee_tier1_price', '3.0', 'NUMBER', '水费第一阶梯单价(元/吨)'),
('water_fee_tier1_limit', '20', 'NUMBER', '水费第一阶梯用量上限(吨)'),
('water_fee_tier2_price', '4.0', 'NUMBER', '水费第二阶梯单价(元/吨)'),
('water_fee_tier2_limit', '50', 'NUMBER', '水费第二阶梯用量上限(吨)'),
('water_fee_tier3_price', '5.0', 'NUMBER', '水费第三阶梯单价(元/吨)'),

-- 电费配置
('electric_fee_tier1_price', '0.5', 'NUMBER', '电费第一阶梯单价(元/度)'),
('electric_fee_tier1_limit', '200', 'NUMBER', '电费第一阶梯用量上限(度)'),
('electric_fee_tier2_price', '0.6', 'NUMBER', '电费第二阶梯单价(元/度)'),
('electric_fee_tier2_limit', '400', 'NUMBER', '电费第二阶梯用量上限(度)'),
('electric_fee_tier3_price', '0.8', 'NUMBER', '电费第三阶梯单价(元/度)'),

-- 燃气费配置
('gas_fee_tier1_price', '2.5', 'NUMBER', '燃气费第一阶梯单价(元/立方米)'),
('gas_fee_tier1_limit', '100', 'NUMBER', '燃气费第一阶梯用量上限(立方米)'),
('gas_fee_tier2_price', '3.0', 'NUMBER', '燃气费第二阶梯单价(元/立方米)'),
('gas_fee_tier2_limit', '200', 'NUMBER', '燃气费第二阶梯用量上限(立方米)'),
('gas_fee_tier3_price', '3.5', 'NUMBER', '燃气费第三阶梯单价(元/立方米)'),

-- 停车费配置
('parking_fee_monthly', '200', 'NUMBER', '停车费月租(元/位/月)'),

-- 电梯费配置
('elevator_fee_calculation_method', 'BY_ROOM', 'STRING', '电梯费计算方式(BY_AREA-按面积,BY_ROOM-按户)'),
('elevator_fee_unit_price_area', '0.5', 'NUMBER', '电梯费按面积单价(元/平方米)'),
('elevator_fee_monthly_per_room', '30', 'NUMBER', '电梯费按户收费(元/户/月)'),

-- 垃圾费配置
('garbage_fee_calculation_method', 'BY_ROOM', 'STRING', '垃圾费计算方式(BY_AREA-按面积,BY_PERSON-按人数,BY_ROOM-按户)'),
('garbage_fee_unit_price_area', '0.3', 'NUMBER', '垃圾费按面积单价(元/平方米)'),
('garbage_fee_unit_price_person', '15', 'NUMBER', '垃圾费按人数单价(元/人/月)'),
('garbage_fee_monthly_per_room', '20', 'NUMBER', '垃圾费按户收费(元/户/月)'),

-- 房屋空置费配置
('vacancy_fee_rate', '0.5', 'NUMBER', '房屋空置费率(相对于物业费的比例,0.5表示50%)'),

-- 其他配置
('bill_due_days', '30', 'NUMBER', '账单到期天数');

-- 创建索引优化查询性能
CREATE INDEX `idx_bills_room_status_month` ON `bills` (`room_id`, `status`, `bill_month`);
CREATE INDEX `idx_payments_status_time` ON `payments` (`status`, `create_time`);
CREATE INDEX `idx_usage_records_room_month` ON `usage_records` (`room_id`, `usage_month`);