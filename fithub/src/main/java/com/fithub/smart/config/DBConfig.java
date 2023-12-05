package com.fithub.smart.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.fithub.smart.model.dao")
public class DBConfig {

}
