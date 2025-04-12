package com.sharedexp;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseDTO {
    private Long id;
    private Long groupId;
    private String description;
    private double amount;
    private Long paidBy; // User Id
    private List<Long> participants;
}
