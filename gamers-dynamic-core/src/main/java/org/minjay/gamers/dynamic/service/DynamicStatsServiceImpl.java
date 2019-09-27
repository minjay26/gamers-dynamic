package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.data.domain.DynamicStats;
import org.minjay.gamers.dynamic.data.repository.DynamicStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicStatsServiceImpl extends EntityServiceSupport<DynamicStats, String, DynamicStatsRepository> implements DynamicStatsService {

    @Autowired
    public DynamicStatsServiceImpl(DynamicStatsRepository repository) {
        super(repository);
    }
}
