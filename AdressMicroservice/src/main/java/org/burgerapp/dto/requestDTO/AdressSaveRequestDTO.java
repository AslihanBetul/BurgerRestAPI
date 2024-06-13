package org.burgerapp.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdressSaveRequestDTO {
    private String token;
    private String name;
    private String no;
    private String street;
    private String city;
}
