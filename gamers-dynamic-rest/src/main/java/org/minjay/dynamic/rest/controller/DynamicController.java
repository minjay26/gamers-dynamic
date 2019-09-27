package org.minjay.dynamic.rest.controller;

import org.minjay.dynamic.rest.util.HttpUtils;
import org.minjay.gamers.dynamic.data.domain.DynamicReply;
import org.minjay.gamers.dynamic.data.domain.DynamicStats;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.DynamicReplyService;
import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.dynamic.service.DynamicStatsService;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.dynamic.service.model.DynamicReplyDto;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private DynamicReplyService dynamicReplyService;
    @Autowired
    private DynamicStatsService dynamicStatsService;

    @GetMapping
    public ResponseEntity<Collection<Dynamic>> search(@ModelAttribute SearchCriteria criteria,
                                                      @PageableDefault Pageable pageable,
                                                      @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(dynamicService.search(criteria, loginUser.getUserId(), pageable));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated DynamicDto dynamic, @AuthenticationPrincipal LoginUser loginUser,
                                       HttpServletRequest request) {
        dynamic.setUserId(loginUser.getUserId());
        dynamic.setUsername(loginUser.getUsername());
        dynamic.setIp(HttpUtils.getIpAddr(request));
        dynamicService.create(dynamic);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable("id") String dynamicId, @AuthenticationPrincipal LoginUser loginUser) {
        dynamicService.like(dynamicId, loginUser.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<Void> reply(@PathVariable("id") String id,
                                      @RequestBody DynamicReply reply,
                                      @AuthenticationPrincipal LoginUser loginUser) {
        reply.setUserId(loginUser.getUserId());
        reply.setDynamicId(id);
        if (reply.getParentId() != null) {
            reply.setDepth(2);
        }
        dynamicReplyService.save(reply);
        DynamicStats stats = dynamicStatsService.findById(id);
        if (stats == null) {
            stats = new DynamicStats(id);
        }
        stats.setReplyCount(stats.getReplyCount() + 1);
        dynamicStatsService.save(stats);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<DynamicReplyDto>> getReplies(@PathVariable("id") String id) {
        return ResponseEntity.ok(dynamicReplyService.getReplies(id));
    }

}
