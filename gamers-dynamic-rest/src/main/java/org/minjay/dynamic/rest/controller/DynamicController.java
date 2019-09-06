package org.minjay.dynamic.rest.controller;

import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.dynamic.service.model.DynamicDto;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated DynamicDto dynamic, @AuthenticationPrincipal LoginUser loginUser) {
        dynamic.setUserId(loginUser.getUserId());
        dynamicService.create(dynamic);
        return ResponseEntity.ok().build();
    }
}
