package com.herbal.controller;

import com.herbal.domain.THerbs;
import com.herbal.service.THerbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/herbs")
public class THerbsController {

    @Autowired
    private THerbsService herbsService;

    @GetMapping("/top20")
    public List<THerbs> getTop20Herbs() {
        return herbsService.getTop20Herbs();
    }
}
