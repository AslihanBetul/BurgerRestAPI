package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
}
