package org.burgerapp.dto.requestDTO;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class ProductSaveDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private CookType cookType;


    private List<Long> removableItemsIds = new ArrayList<>();

    private List<Long> availableItemsIds = new ArrayList<>();

    private List<Long> drinksIds = new ArrayList<>();

    private Long categoryId;
}
