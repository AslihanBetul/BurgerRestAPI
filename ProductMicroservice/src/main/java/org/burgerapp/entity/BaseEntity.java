package org.burgerapp.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class BaseEntity {
    @Builder.Default
    private Long createAt=System.currentTimeMillis();
    private Long updateAt;
}
