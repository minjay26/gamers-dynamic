package org.minjay.gamers.dynamic.client.cloud.feign;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gamers-accounts", path = "/accounts")
public interface AccountFeignClient extends AccountClient {

    @Override
    @PutMapping("/create_dynamic_action")
    void publishCreateDynamicAction(@RequestParam("userId") Long userId);
}
