package org.burgerapp.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomProductRequestDTO {
    private Long productId;
    private List<Long> removableItems;
    private List<Long> availableItems;
    private List<Long> drinks;
}
