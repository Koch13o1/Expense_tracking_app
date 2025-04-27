package com.sharedexp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementTransactionDTO {
    private Long fromUser;
    private Long toUser;
    private Double amount;
}
