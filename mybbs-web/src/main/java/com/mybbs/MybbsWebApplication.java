package com.mybbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mybbs.mapper")
public class MybbsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybbsWebApplication.class, args);
    }
}
