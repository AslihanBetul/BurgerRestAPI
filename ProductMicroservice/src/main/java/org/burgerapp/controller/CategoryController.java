package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.entity.Product;
import org.burgerapp.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CategoryController {
    private final CategoryService categoryService;

}
