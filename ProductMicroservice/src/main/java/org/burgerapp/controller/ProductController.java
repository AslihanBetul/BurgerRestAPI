package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.CustomProductRequestDTO;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDetailedDTO;
import org.burgerapp.entity.Product;
import org.burgerapp.rabitmq.model.CustomProductModel;
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
    @GetMapping("/findbyid")
    public ResponseEntity<ProductResponseDetailedDTO> findbyid(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }
    @PostMapping("/cuntomproduct")
    public ResponseEntity<CustomProductModel> customProduct(@RequestBody CustomProductRequestDTO customProductRequestDTO){
        return ResponseEntity.ok(productService.customProduct(customProductRequestDTO));
    }
}
