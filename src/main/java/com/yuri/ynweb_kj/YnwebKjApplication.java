package com.yuri.ynweb_kj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.yuri.ynweb_kj.dao")
@ComponentScan(basePackages = {"com.yuri.ynweb_kj.*"})
public class YnwebKjApplication {

    public static void main(String[] args) {
        SpringApplication.run(YnwebKjApplication.class, args);
    }
}
