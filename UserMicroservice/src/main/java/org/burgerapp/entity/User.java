package org.burgerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.burgerapp.entity.enums.UserStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class User {
    @MongoId
    private String id;
    private Long authId;
    private String username;
    private String email;
    private UserStatus userStatus;
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
