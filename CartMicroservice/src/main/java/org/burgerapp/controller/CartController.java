package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

}
