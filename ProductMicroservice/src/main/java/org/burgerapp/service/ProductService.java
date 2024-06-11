package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
