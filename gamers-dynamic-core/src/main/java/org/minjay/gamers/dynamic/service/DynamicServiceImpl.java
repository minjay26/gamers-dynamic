package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.data.elasticsearch.repository.DynamicRepository;
import org.minjay.gamers.dynamic.mq.MqConstants;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DynamicRepository dynamicRepository;

    @Override
    public void create(DynamicDto dynamic) {
        sendToMq(dynamic);
    }

    @Override
    public Collection<Dynamic> search(SearchCriteria searchCriteria, Pageable pageable) {
        return dynamicRepository.search(searchCriteria, pageable);
    }

    @Async
    public void sendToMq(DynamicDto dynamic) {
        rabbitTemplate.convertAndSend(MqConstants.DYNAMIC_CREATE_EXCHANGE, null, dynamic);
    }
}
