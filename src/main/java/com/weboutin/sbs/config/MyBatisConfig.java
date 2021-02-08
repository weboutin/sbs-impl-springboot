package com.weboutin.sbs.config;

import javax.sql.DataSource;

import com.weboutin.sbs.mappers.UserMapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;

@Configuration
public class MyBatisConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory() throws Exception {
    //     SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    //     factoryBean.setDataSource(dataSource());
    //     return factoryBean.getObject();
    // }

    // @Bean
    // public UserMapper userMapper() throws Exception {
    //     SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
    //     return sqlSessionTemplate.getMapper(UserMapper.class);
    // }
}