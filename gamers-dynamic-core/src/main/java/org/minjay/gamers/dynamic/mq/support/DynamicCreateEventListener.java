package org.minjay.gamers.dynamic.mq.support;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DynamicCreateEventListener implements ApplicationListener<DynamicCreateEvent> {

    @Autowired
    private AccountClient accountClient;

    @Override
    public void onApplicationEvent(DynamicCreateEvent event) {
        accountClient.publishCreateDynamicAction(((Dynamic) event.getSource()).getUserId());
    }

}
