package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDTO;
import org.burgerapp.entity.Category;
import org.burgerapp.entity.Product;
import org.burgerapp.mapper.ProductMapper;
import org.burgerapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<ProductResponseDTO> findByCategoryId(Long categoryId){
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        Category category = categoryService.findById(categoryId);
        productRepository.findByCategory(category).forEach(product -> {
            productResponseDTOS.add(ProductMapper.INSTANCE.productToProductResponseDTO(product));
        });
        return productResponseDTOS;
    }
}
