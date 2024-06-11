package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {
    private final ProductService productService;
}
