package org.minjay.gamers.dynamic.mq.support;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.context.ApplicationEvent;

public class DynamicCreateEvent extends ApplicationEvent {

    public DynamicCreateEvent(Dynamic dynamic) {
        super(dynamic);
    }

}
