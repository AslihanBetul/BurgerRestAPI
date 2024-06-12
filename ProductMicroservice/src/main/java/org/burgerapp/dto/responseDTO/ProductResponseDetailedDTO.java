package org.burgerapp.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.BaseEntity;
import org.burgerapp.entity.Category;
import org.burgerapp.entity.Item;
import org.burgerapp.entity.enums.CookType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductResponseDetailedDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private CookType cookType;
    private Category category;

    private List<Item> removableItems = new ArrayList<>();

    private List<Item> availableItems = new ArrayList<>();

    private List<Item> drinks = new ArrayList<>();



}
