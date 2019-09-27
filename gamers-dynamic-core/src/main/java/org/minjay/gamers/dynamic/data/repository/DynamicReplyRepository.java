package org.minjay.gamers.dynamic.data.repository;

import org.minjay.gamers.dynamic.data.domain.DynamicReply;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DynamicReplyRepository extends PagingAndSortingRepository<DynamicReply, Long> {

    List<DynamicReply> findAllByDynamicId(String dynamicId);
}
