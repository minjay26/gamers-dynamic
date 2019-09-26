package org.minjay.gamers.dynamic.data.repository;

import org.minjay.gamers.dynamic.data.domain.DynamicLike;
import org.springframework.data.repository.CrudRepository;

public interface DynamicLikeRepository extends CrudRepository<DynamicLike, Long> {

    DynamicLike findByDynamicIdAndAndUserId(String dynamicId, Long userId);
}
