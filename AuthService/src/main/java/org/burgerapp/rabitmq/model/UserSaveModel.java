package org.burgerapp.rabitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.enums.AuthStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserSaveModel {
    private Long authId;
    private String username;
    private String email;
    private AuthStatus authStatus;
}
