package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.minjay.gamers.dynamic.data.domain.DynamicLike;
import org.minjay.gamers.dynamic.data.domain.DynamicStats;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.data.elasticsearch.repository.DynamicRepository;
import org.minjay.gamers.dynamic.data.repository.DynamicLikeRepository;
import org.minjay.gamers.dynamic.data.repository.DynamicStatsRepository;
import org.minjay.gamers.dynamic.mq.MqConstants;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DynamicRepository dynamicRepository;
    @Autowired
    private DynamicLikeRepository dynamicLikeRepository;
    @Autowired
    private DynamicStatsRepository dynamicStatsRepository;
    @Autowired
    private AccountClient accountClient;

    @Override
    public void create(DynamicDto dynamic) {
        sendToMq(dynamic);
    }

    @Override
    public void like(String dynamicId, Long userId) {
        DynamicLike like = dynamicLikeRepository.findByDynamicIdAndAndUserId(dynamicId, userId);
        DynamicStats stats = dynamicStatsRepository.findById(dynamicId).orElse(null);
        if (like != null) {
            stats.setLikeCount(stats.getLikeCount() - 1);
            dynamicStatsRepository.save(stats);
            dynamicLikeRepository.delete(like);
            return;
        }

        if (stats == null) {
            stats = new DynamicStats(dynamicId);
        }
        stats.setLikeCount(stats.getLikeCount() + 1);
        dynamicStatsRepository.save(stats);

        like = new DynamicLike(dynamicId, userId);
        dynamicLikeRepository.save(like);
    }

    @Override
    public Collection<Dynamic> search(SearchCriteria searchCriteria, Long userId, Pageable pageable) {
        searchCriteria.getUserIds().add(userId);
        if (searchCriteria.isIncludeFocus()) {
            List<Map<String, Object>> focus = accountClient.getFocus(userId);
            for (Map<String, Object> item : focus) {
                searchCriteria.getUserIds().add(Long.parseLong(item.get("toUserId").toString()));
            }
        }
        return dynamicRepository.search(searchCriteria, pageable);
    }

    @Async
    public void sendToMq(DynamicDto dynamic) {
        rabbitTemplate.convertAndSend(MqConstants.DYNAMIC_CREATE_EXCHANGE, null, dynamic);
    }
}
