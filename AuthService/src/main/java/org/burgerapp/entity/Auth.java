package org.burgerapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.enums.AuthRole;
import org.burgerapp.entity.enums.AuthStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true)
    @Length(min = 3,max = 30)
    private String  username;
    @Length(min = 8,max = 32)
    private String  password;
    @Column(unique = true)
    @Email
    private String email;
    private String activationCode;
    private String rePasswordCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    AuthStatus authStatus=AuthStatus.PENDING;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    AuthRole authRole=AuthRole.USER;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();

    private Long updateAt;

}
