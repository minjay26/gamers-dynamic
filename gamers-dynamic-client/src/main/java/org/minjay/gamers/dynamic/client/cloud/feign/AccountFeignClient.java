package org.minjay.gamers.dynamic.client.cloud.feign;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "account-service", path = "/accounts")
public interface AccountFeignClient extends AccountClient {

    @Override
    @PutMapping("/create_dynamic_action")
    void publishCreateDynamicAction();
}
