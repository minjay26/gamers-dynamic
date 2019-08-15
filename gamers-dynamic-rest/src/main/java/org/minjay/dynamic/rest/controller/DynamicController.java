package org.minjay.dynamic.rest.controller;

import org.minjay.gamers.dynamic.domain.Dynamic;
import org.minjay.gamers.dynamic.mapper.DynamicMapper;
import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicMapper dynamicMapper;

    @GetMapping
    public ResponseEntity<Dynamic> test(@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request) {
        return ResponseEntity.ok(dynamicMapper.findById(1L));
    }
}
