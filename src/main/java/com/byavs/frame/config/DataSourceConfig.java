package com.byavs.frame.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;

/**
 * @author XuYang
 * @description: Druid数据源配置
 * @date 2019/3/2110:47
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.byavs.frame.dao.mapper"})
public class DataSourceConfig {

    //@Bean
    public DruidDataSource dataSource() throws PropertyVetoException {


        return null;
    }
}
