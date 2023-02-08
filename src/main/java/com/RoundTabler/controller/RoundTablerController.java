package com.RoundTabler.controller;

import com.RoundTabler.models.RoundTablerRequest;
import com.RoundTabler.services.RoundTablerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoundTablerController {

    @Autowired
    RoundTablerService roundTablerService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @PostMapping("/send-roundtabler-request")
    public void getRoundTablerRequest(@RequestBody RoundTablerRequest roundTablerRequest) {
        roundTablerService.doSomethingWithTheData(roundTablerRequest);
    }
}
