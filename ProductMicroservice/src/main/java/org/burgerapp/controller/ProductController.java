package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.entity.Product;
import org.burgerapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.burgerapp.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {
    private final ProductService productService;
    @PostMapping(SAVE)
    public ResponseEntity<Product> save(@RequestBody ProductSaveDTO productSaveDTO) {
        return ResponseEntity.ok(productService.save(productSaveDTO));
    }
}
