package org.burgerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Order {
    @MongoId
    private String id;
    private String userId;
    private List<Product> products;
    private Adress adress;
    private BigDecimal totalPrice;
}
