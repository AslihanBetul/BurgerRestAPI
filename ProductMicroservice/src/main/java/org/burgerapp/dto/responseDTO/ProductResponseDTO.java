package org.burgerapp.dto.responseDTO;

import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
public class ProductResponseDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private CookType cookType;


    private List<Item> removableItems = new ArrayList<>();

    private List<Item> availableItems = new ArrayList<>();

    private List<Item> drinks = new ArrayList<>();

    private Category category;


    private BaseEntity baseEntity;
}
