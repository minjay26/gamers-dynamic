package org.minjay.dynamic.rest.controller;

import org.joda.time.DateTime;
import org.minjay.gamers.dynamic.data.domain.Dynamic;
import org.minjay.gamers.dynamic.service.DynamicService;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated Dynamic dynamic, @AuthenticationPrincipal LoginUser loginUser) {
        dynamic.setUserId(loginUser.getUserId());
        dynamic.setCreatedDate(DateTime.now());
        dynamic.setLastModifiedDate(DateTime.now());
        dynamicService.create(dynamic);
        return ResponseEntity.ok().build();
    }
}
