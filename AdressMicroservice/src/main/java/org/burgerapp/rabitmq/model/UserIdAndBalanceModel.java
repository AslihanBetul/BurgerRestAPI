package org.burgerapp.rabitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserIdAndBalanceModel {
    private String userId;
    private BigDecimal balance;
}
