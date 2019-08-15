package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.data.domain.Dynamic;
import org.minjay.gamers.dynamic.data.mapper.DynamicMapper;
import org.minjay.gamers.dynamic.mq.MqConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void create(Dynamic dynamic) {
        dynamicMapper.save(dynamic);
        sendToMq(dynamic);
    }

    @Async
    public void sendToMq(Dynamic dynamic) {
        rabbitTemplate.convertAndSend(MqConstants.DYNAMIC_CREATE_EXCHANGE, null, dynamic);
    }
}
