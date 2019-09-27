package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.data.domain.DynamicReply;
import org.minjay.gamers.dynamic.service.model.DynamicReplyDto;

import java.util.List;

public interface DynamicReplyService extends EntityService<DynamicReply, Long> {

    List<DynamicReplyDto> getReplies(String dynamicId);
}
