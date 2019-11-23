package com.java.sale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.java.sale.dao.mapper")
@SpringBootApplication
public class SaleApplication{
    public static void main(String[] args) {
        SpringApplication.run(SaleApplication.class, args);
    }
}
