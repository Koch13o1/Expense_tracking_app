package com.sharedexp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBalanceDTO {
    private Long userId;
    private double balance;
}
