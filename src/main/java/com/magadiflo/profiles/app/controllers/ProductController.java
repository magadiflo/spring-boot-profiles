package com.magadiflo.profiles.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @GetMapping
    public String showMessage() {
        return "Hola";
    }

}
