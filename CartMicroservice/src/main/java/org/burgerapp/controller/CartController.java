package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam String token) {
        return ResponseEntity.ok(cartService.pay(token));
    }

}
