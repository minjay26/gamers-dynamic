package org.minjay.gamers.dynamic.client.cloud.feign;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "gamers-accounts", fallbackFactory = AccountFeignClientFallbackFactory.class,
        configuration = AccountFeignClient.AccountFeignClientConfig.class)
public interface AccountFeignClient extends AccountClient {

    @Override
    @PutMapping("/accounts/create_dynamic_action")
    void publishCreateDynamicAction(@RequestParam("userId") Long userId);

    @Override
    @GetMapping("/users/{id}/focus")
    List<Map<String, Object>> getFocus(@PathVariable("id") Long userId);

    @Override
    @GetMapping("/users/{id}/username")
    String getUsernameByUserId(@PathVariable("id") Long userId);

    @Configuration
    class AccountFeignClientConfig {
        @Bean
        public AccountFeignClientFallbackFactory accountFeignClientFallbackFactory() {
            return new AccountFeignClientFallbackFactory();
        }
    }
}
