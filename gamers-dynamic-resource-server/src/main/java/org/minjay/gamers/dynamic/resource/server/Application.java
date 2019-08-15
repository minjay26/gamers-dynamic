package org.minjay.gamers.dynamic.resource.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.minjay.gamers.dynamic.mapper.DynamicMapper;
import org.minjay.gamers.security.jackson.SecurityLoginJackson2Module;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SecurityLoginJackson2Module());
        return objectMapper;
    }

    @Configuration
    @MapperScan(basePackageClasses = DynamicMapper.class)
    static class MybatisConfig {

    }

}
