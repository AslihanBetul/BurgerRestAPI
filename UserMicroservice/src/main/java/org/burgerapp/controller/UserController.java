package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {
    private final UserService userService;
}
