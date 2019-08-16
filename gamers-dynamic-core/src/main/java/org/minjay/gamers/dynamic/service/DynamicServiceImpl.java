package org.minjay.gamers.dynamic.service;

import org.joda.time.DateTime;
import org.minjay.gamers.dynamic.mq.MqConstants;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void create(DynamicDto dynamic) {
        sendToMq(dynamic);
    }

    @Async
    public void sendToMq(DynamicDto dynamic) {
        rabbitTemplate.convertAndSend(MqConstants.DYNAMIC_CREATE_EXCHANGE, null, dynamic);
    }
}
