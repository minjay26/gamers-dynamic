package org.minjay.dynamic.rest.controller;

import org.minjay.dynamic.rest.util.HttpUtils;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RefreshScope
@RestController("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated DynamicDto dynamic, @AuthenticationPrincipal LoginUser loginUser,
                                       HttpServletRequest request) {
        dynamic.setUserId(loginUser.getUserId());
        dynamic.setUsername(loginUser.getUsername());
        dynamic.setId(HttpUtils.getIpAddr(request));
        dynamicService.create(dynamic);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Dynamic>> search(@ModelAttribute SearchCriteria criteria, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dynamicService.search(criteria, pageable));
    }
}
