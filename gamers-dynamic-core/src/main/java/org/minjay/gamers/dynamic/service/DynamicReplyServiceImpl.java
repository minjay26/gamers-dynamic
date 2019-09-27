package org.minjay.gamers.dynamic.service;

import org.minjay.gamers.dynamic.client.AccountClient;
import org.minjay.gamers.dynamic.data.domain.DynamicReply;
import org.minjay.gamers.dynamic.data.repository.DynamicReplyRepository;
import org.minjay.gamers.dynamic.service.model.DynamicReplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamicReplyServiceImpl extends EntityServiceSupport<DynamicReply, Long, DynamicReplyRepository> implements DynamicReplyService {

    private final AccountClient accountClient;

    @Autowired
    public DynamicReplyServiceImpl(DynamicReplyRepository repository, AccountClient accountClient) {
        super(repository);
        this.accountClient = accountClient;
    }

    @Override
    public List<DynamicReplyDto> getReplies(String dynamicId) {
        List<DynamicReply> replies = getRepository().findAllByDynamicId(dynamicId);
        Map<Long, List<DynamicReply>> map = replies.stream().collect(Collectors.groupingBy(DynamicReply::getParentId));
        List<DynamicReply> depth1s = map.get(0L);

        List<DynamicReplyDto> dtos = new ArrayList<>();
        for (DynamicReply reply : depth1s) {
            DynamicReplyDto dto = DynamicReplyDto.fromDynamicReply(reply);
            dto.setUsername(accountClient.getUsernameByUserId(reply.getUserId()));
            List<DynamicReply> subs = map.get(reply.getId());
            if (!CollectionUtils.isEmpty(subs)) {
                List<DynamicReplyDto.SubReply> subReplies = new ArrayList<>();
                for (DynamicReply sub : subs) {
                    DynamicReplyDto.SubReply subReply = new DynamicReplyDto.SubReply().fromDynamicReply(sub);
                    subReply.setUsername(accountClient.getUsernameByUserId(sub.getUserId()));
                    if (sub.getDepth() > 2) {
                        subReply.setReplyUsername(accountClient.getUsernameByUserId(sub.getReplyUserId()));
                    }
                    subReplies.add(subReply);
                }
                dto.setSubReplies(subReplies);
            }
            dtos.add(dto);
        }
        return dtos;
    }
}
