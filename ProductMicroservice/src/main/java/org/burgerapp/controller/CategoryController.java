package org.burgerapp.controller;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.CategorySaveDTO;
import org.burgerapp.entity.Product;
import org.burgerapp.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.burgerapp.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(SAVE)
    public ResponseEntity<String> save(@RequestBody CategorySaveDTO categorySaveDTO){
        return ResponseEntity.ok(categoryService.save(categorySaveDTO));
    }
}
