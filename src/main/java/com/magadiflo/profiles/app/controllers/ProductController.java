package com.magadiflo.profiles.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Value("${server.port}")
    private Integer port;
    @Value("${app.info}")
    private String info;
    @Value("${app.parametro}")
    private String parameter;
    @Value("${base.datos.fuente.url}")
    private String url;

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    private final Environment environment;

    public ProductController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public Map<String, Object> showMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("port", port);
        response.put("info", info);
        response.put("parameter", parameter);
        response.put("url", url);
        response.put("profiles-environment", this.getProfiles());
        response.put("profiles-properties", activeProfiles);

        return response;
    }

    private String getProfiles() {
        return String.join(", ", environment.getActiveProfiles());
    }

}
