package org.minjay.gamers.dynamic.mq.support;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DynamicCreateEventListener implements ApplicationListener<DynamicCreateEvent> {

    @Override
    public void onApplicationEvent(DynamicCreateEvent event) {
        Dynamic dynamic = event.getDynamic();
    }
}
