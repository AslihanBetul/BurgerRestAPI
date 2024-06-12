package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.entity.Product;
import org.burgerapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ItemService itemService;
    private final CategoryService categoryService;


    public Product save(ProductSaveDTO productSaveDTO) {
        Product product = Product.builder()
                .name(productSaveDTO.getName())
                .description(productSaveDTO.getDescription())
                .price(productSaveDTO.getPrice())
                .category(categoryService.findById(productSaveDTO.getCategoryId()))
                .removableItems(itemService.findAllByIds(productSaveDTO.getRemovableItemsIds()))
                .availableItems(itemService.findAllByIds(productSaveDTO.getAvailableItemsIds()))
                .build();
        productRepository.save(product);
        return product;
    }
}
