package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDTO;
import org.burgerapp.entity.Product;
import org.burgerapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.burgerapp.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping(SAVE)
    public ResponseEntity<Product> save(@RequestBody ProductSaveDTO productSaveDTO) {
        return ResponseEntity.ok(productService.save(productSaveDTO));
    }
    @GetMapping("/findbycategory")
    public ResponseEntity<List<ProductResponseDTO>> findbycategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }
}
