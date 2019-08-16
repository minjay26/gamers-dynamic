package org.minjay.gamers.dynamic.data.elasticsearch.repository;

import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DynamicRepository extends ElasticsearchRepository<Dynamic, String> {
}
