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
import org.burgerapp.rabitmq.model.CustomProductModel;
import org.burgerapp.repository.ProductRepository;
import org.burgerapp.utility.JwtTokenManager;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitTemplate rabbitTemplate;
    private final String directExchangeProduct = "directExchangeProduct";


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

    public CustomProductModel customProduct(CustomProductRequestDTO customProductRequestDTO) {
        Product choosenProduct= productRepository.findById(customProductRequestDTO.getProductId()).orElseThrow(() -> new ItemServiceException(PRODUCT_NOT_FOUND));

        List<Item> itemsShouldBeRemovedFromProduct = itemService.findAllByIds(customProductRequestDTO.getRemovableItems());
        choosenProduct.getRemovableItems().removeAll(itemsShouldBeRemovedFromProduct);

        List<Item> itemsShouldBeAddProduct = itemService.findAllByIds(customProductRequestDTO.getAvailableItems());
        choosenProduct.getAvailableItems().clear();
        choosenProduct.getAvailableItems().addAll(itemsShouldBeAddProduct);

        choosenProduct.getDrinks().addAll(itemService.findAllByIds(customProductRequestDTO.getDrinks()));

        BigDecimal totalPrice = calculateProductPrice(choosenProduct);
        choosenProduct.setPrice(totalPrice);

        Long authId = jwtTokenManager.getAuthIdFromToken(customProductRequestDTO.getToken()).orElseThrow(() -> new ItemServiceException(INVALID_TOKEN));

        String userId = convertSentAndReceiveUserId(authId);

        CustomProductModel customProductModel = CustomProductModel.builder().userId(userId).product(choosenProduct).build();
        //TODO CustomProductModel cart'a göndereilecek
        return customProductModel;
    }
    private void convertAndSendCustomProductModel(CustomProductModel customProductModel) {
        String keySendProduct = "keySendProduct";
        rabbitTemplate.convertAndSend(directExchangeProduct, keySendProduct,customProductModel);
    }
    private String convertSentAndReceiveUserId(Long authId){
        String keyGetUserId = "keyGetUserId";
        return (String) rabbitTemplate.convertSendAndReceive(directExchangeProduct, keyGetUserId, authId);
    }

    private BigDecimal calculateProductPrice(Product product){
        BigDecimal productPrice = product.getPrice();

        if(!product.getAvailableItems().isEmpty()){
            for (Item item : product.getAvailableItems()) {
                productPrice = productPrice.add(item.getPrice());
            }
        }
        if(!product.getDrinks().isEmpty()){
            for (Item item : product.getDrinks()) {
                productPrice = productPrice.add(item.getPrice());
            }
        }
        return productPrice;
    }
}
