package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface DynamicService {

    void create(DynamicDto dynamic);

    void like(String dynamicId, Long userId);

    Collection<Dynamic> search(SearchCriteria searchCriteria, Long userId, Pageable pageable);

}
