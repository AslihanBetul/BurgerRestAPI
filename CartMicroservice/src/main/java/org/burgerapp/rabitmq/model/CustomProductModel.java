package org.burgerapp.rabitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.Product;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomProductModel {
    String userId;
    Product product;
}
