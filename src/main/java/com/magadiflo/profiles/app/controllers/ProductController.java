package com.magadiflo.profiles.app.controllers;

import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping
    public Map<String, Object> showMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("saludo", "Hola Mundo");
        response.put("puerto", port);

        return response;
    }

}
