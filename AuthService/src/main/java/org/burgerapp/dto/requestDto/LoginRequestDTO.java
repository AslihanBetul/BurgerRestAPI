package org.burgerapp.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email boş olamaz")
    @Email
    private String email;
    @NotBlank(message = "şifreniz boş olamaz")
    @Length(min = 8,max = 32)
    private String  password;
}
