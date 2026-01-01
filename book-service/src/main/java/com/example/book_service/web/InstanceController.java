package com.example.book_service.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/api/debug/instance")
    public String instance() {
        String host = System.getenv().getOrDefault("HOSTNAME", "local");
        return "instance=" + host + " internalPort=" + port;
    }
}