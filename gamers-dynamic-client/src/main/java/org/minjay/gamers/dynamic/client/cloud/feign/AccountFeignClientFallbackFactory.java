package org.minjay.gamers.dynamic.client.cloud.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccountFeignClientFallbackFactory implements FallbackFactory<AccountFeignClient> {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountFeignClientFallbackFactory.class);

    @Override
    public AccountFeignClient create(Throwable cause) {
        LOGGER.error("account feign call error {}", cause.getMessage());
        return new AccountFeignClient() {
            @Override
            public void publishCreateDynamicAction(Long userId) {

            }

            @Override
            public List<Map<String, Object>> getFocus(Long userId) {
                return Collections.emptyList();
            }
        };
    }
}
