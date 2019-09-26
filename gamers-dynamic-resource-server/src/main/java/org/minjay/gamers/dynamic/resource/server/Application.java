package org.minjay.gamers.dynamic.resource.server;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.loadbalancer.IRule;
import feign.RequestInterceptor;
import org.minjay.gamers.dynamic.client.cloud.feign.AccountFeignClient;
import org.minjay.gamers.dynamic.data.elasticsearch.repository.DynamicRepository;
import org.minjay.gamers.dynamic.mq.RabbitConfig;
import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.security.jackson.SecurityLoginJackson2Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RefreshScope
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

    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackages = "org.minjay.gamers.dynamic.data.repository")
    @EnableJpaAuditing
    @EntityScan(basePackages = "org.minjay.gamers.dynamic.data.domain")
    static class JpaConfig {
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

    @Configuration
    @EnableFeignClients(basePackageClasses = AccountFeignClient.class)
    static class ClientConfig {
        @Bean
        public IRule rule() {
            return new NacosRule();
        }

        @Bean
        public RequestInterceptor requestTokenBearerInterceptor() {
            return requestTemplate -> {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
                        SecurityContextHolder.getContext().getAuthentication()
                                .getDetails();
                requestTemplate.header("Authorization",
                        details.getTokenValue());
            };
        }

    }

}
