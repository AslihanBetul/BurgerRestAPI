package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

}
