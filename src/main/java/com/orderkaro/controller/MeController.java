package com.orderkaro.controller;

import com.orderkaro.config.CurrentUser;
import com.orderkaro.config.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {
    @GetMapping
    public UserContext me() {
        log.debug("current user name {} email {}", CurrentUser.userId(), CurrentUser.email());
        return CurrentUser.get();
    }
}
