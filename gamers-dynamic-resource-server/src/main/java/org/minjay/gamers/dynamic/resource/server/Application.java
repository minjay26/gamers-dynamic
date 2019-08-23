package org.minjay.gamers.dynamic.resource.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.minjay.gamers.dynamic.client.cloud.feign.AccountFeignClient;
import org.minjay.gamers.dynamic.data.elasticsearch.repository.DynamicRepository;
import org.minjay.gamers.dynamic.mq.RabbitConfig;
import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.security.jackson.SecurityLoginJackson2Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties
@Import(RabbitConfig.class)
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
    @ComponentScan(basePackageClasses = DynamicService.class)
    static class ServiceConfig {
    }

    @Configuration
    static class MybatisConfig {
    }

    @Configuration
    @EnableElasticsearchRepositories(basePackageClasses = DynamicRepository.class)
    static class ElasticsearchConfig {
    }

    @EnableFeignClients(basePackageClasses = AccountFeignClient.class)
    static class ClientConfig {

    }

}
