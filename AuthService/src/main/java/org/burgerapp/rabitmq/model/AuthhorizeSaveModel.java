package org.burgerapp.rabitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.enums.AuthRole;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthhorizeSaveModel {
    private Long authId;
    public AuthRole authRole;
}
