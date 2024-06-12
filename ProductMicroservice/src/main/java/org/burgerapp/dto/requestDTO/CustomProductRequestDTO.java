package org.burgerapp.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.enums.CookType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomProductRequestDTO {
    private String token;
    private Long productId;
    private CookType cookType;
    private List<Long> removableItems;
    private List<Long> availableItems;
    private List<Long> drinks;
}
