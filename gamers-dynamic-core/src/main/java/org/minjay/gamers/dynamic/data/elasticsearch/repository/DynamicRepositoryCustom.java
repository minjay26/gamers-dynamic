package org.minjay.gamers.dynamic.data.elasticsearch.repository;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface DynamicRepositoryCustom {

    Collection<Dynamic> search(SearchCriteria searchCriteria, Pageable pageable);
}
