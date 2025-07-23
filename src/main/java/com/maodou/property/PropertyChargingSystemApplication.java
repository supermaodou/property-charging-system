package com.maodou.property;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.maodou.property.mapper")
public class PropertyChargingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyChargingSystemApplication.class, args);
    }

}
