package org.minjay.gamers.dynamic.mq.support;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.context.ApplicationEvent;

public class DynamicCreateEvent extends ApplicationEvent {

    private final Dynamic dynamic;

    public DynamicCreateEvent(Dynamic dynamic) {
        super(null);
        this.dynamic = dynamic;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }
}
