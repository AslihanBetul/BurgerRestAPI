package org.burgerapp.service;

import lombok.RequiredArgsConstructor;
import org.burgerapp.dto.requestDTO.CustomProductRequestDTO;
import org.burgerapp.dto.requestDTO.ProductSaveDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDTO;
import org.burgerapp.dto.responseDTO.ProductResponseDetailedDTO;
import org.burgerapp.entity.Category;
import org.burgerapp.entity.Item;
import org.burgerapp.entity.Product;
import org.burgerapp.exception.ItemServiceException;
import org.burgerapp.mapper.ProductMapper;
import org.burgerapp.repository.ProductRepository;
import org.burgerapp.utility.JwtTokenManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.burgerapp.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final JwtTokenManager jwtTokenManager;


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

    public ProductResponseDetailedDTO findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ItemServiceException(PRODUCT_NOT_FOUND));
        return ProductMapper.INSTANCE.productToProductResponseDetailedDTO(product);
    }

    public Product customProduct(CustomProductRequestDTO customProductRequestDTO) {
        Product choosenProduct= productRepository.findById(customProductRequestDTO.getProductId()).orElseThrow(() -> new ItemServiceException(PRODUCT_NOT_FOUND));

        List<Item> itemsShouldBeRemovedFromProduct = itemService.findAllByIds(customProductRequestDTO.getRemovableItems());
        choosenProduct.getRemovableItems().removeAll(itemsShouldBeRemovedFromProduct);

        List<Item> itemsShouldBeAddProduct = itemService.findAllByIds(customProductRequestDTO.getAvailableItems());
        choosenProduct.getAvailableItems().addAll(itemsShouldBeAddProduct);

        choosenProduct.getDrinks().addAll(itemService.findAllByIds(customProductRequestDTO.getDrinks()));

        BigDecimal totalPrice = calculateProductPrice(choosenProduct);
        choosenProduct.setPrice(totalPrice);
        //TODO gelecek token ile autId'den userId istenecek ve tasarımdaki model'a eklenecek
        return choosenProduct;
    }
    private BigDecimal calculateProductPrice(Product product){
        BigDecimal productPrice = product.getPrice();

        if(!product.getAvailableItems().isEmpty()){
            product.getAvailableItems().forEach(item -> {
                productPrice.add(item.getPrice());
            });
        }
        if(!product.getDrinks().isEmpty()){
            product.getDrinks().forEach(item -> {
                productPrice.add(item.getPrice());
            });
        }
        return productPrice;
    }
}
