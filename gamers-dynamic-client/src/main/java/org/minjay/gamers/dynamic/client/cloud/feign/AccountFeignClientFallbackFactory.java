package org.minjay.gamers.dynamic.client.cloud.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountFeignClientFallbackFactory implements FallbackFactory<AccountFeignClient> {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccountFeignClientFallbackFactory.class);

    @Override
    public AccountFeignClient create(Throwable cause) {
        LOGGER.error("account feign call error {}", cause.getMessage());
        return new AccountFeignClient() {
            @Override
            public void publishCreateDynamicAction(Long userId) {

            }
        };
    }
}
