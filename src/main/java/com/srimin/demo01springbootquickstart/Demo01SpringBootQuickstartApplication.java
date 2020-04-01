package com.srimin.demo01springbootquickstart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.srimin.demo01springbootquickstart.mapper")
public class Demo01SpringBootQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo01SpringBootQuickstartApplication.class, args);
    }

}
