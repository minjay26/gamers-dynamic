package org.minjay.gamers.dynamic.client.cloud.feign;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gamers-accounts", path = "/accounts", fallbackFactory = AccountFeignClientFallbackFactory.class,
        configuration = AccountFeignClient.AccountFeignClientConfig.class)
public interface AccountFeignClient extends AccountClient {

    @Override
    @PutMapping("/create_dynamic_action")
    void publishCreateDynamicAction(@RequestParam("userId") Long userId);

    @Configuration
    class AccountFeignClientConfig {
        @Bean
        public AccountFeignClientFallbackFactory accountFeignClientFallbackFactory() {
            return new AccountFeignClientFallbackFactory();
        }
    }
}
