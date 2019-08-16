package org.minjay.gamers.dynamic.mq.consumer;

import com.rabbitmq.client.Channel;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.data.elasticsearch.repository.DynamicRepository;
import org.minjay.gamers.dynamic.mq.support.DynamicCreateEvent;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.geo.Point;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DynamicMessageConsumer implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    DynamicRepository dynamicRepository;

    @RabbitListener(queues = "dynamic-create-queue")
    public void saveDynamic(DynamicDto dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        Dynamic dynamic = dto.toDynamic();
        dynamic.setLocation(GeoPoint.fromPoint(new Point(dto.getLat(), dto.getLon())));
        dynamicRepository.save(dynamic);
        channel.basicAck(tag, false);

        applicationContext.publishEvent(new DynamicCreateEvent(dynamic));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
