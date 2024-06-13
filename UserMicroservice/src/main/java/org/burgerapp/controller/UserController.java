package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    private final UserService userService;
    @PostMapping("/balanceupdate")
    public ResponseEntity<String> updateBalance(@RequestParam String token, @RequestParam BigDecimal balance) {
        return ResponseEntity.ok(userService.updateBalance(token,balance));
    }
}
