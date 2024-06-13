package org.burgerapp.rabitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderSaveModel {
    private String userId;
    private List<Product> product=new ArrayList<>();
    private BigDecimal totalPrice;
}
