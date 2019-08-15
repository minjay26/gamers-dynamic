package org.minjay.dynamic.rest.controller;

import org.minjay.gamers.security.userdetails.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/dynamics")
public class DynamicController {

    @GetMapping
    public ResponseEntity<String> test(@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request) {
        return ResponseEntity.ok("aaaaaa");
    }
}
