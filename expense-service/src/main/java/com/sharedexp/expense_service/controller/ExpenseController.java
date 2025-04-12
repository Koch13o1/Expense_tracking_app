package com.sharedexp.expense_service.controller;

import com.sharedexp.UserBalanceDTO;
import com.sharedexp.expense_service.model.Expense;
import com.sharedexp.expense_service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
        Expense savedExpense = expenseService.addExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<Expense>> getExpensesByGroupId(@PathVariable Long groupId){
        List<Expense> expenses = expenseService.getExpenseByGroupId(groupId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/balances/{groupId}")
    public ResponseEntity<List<UserBalanceDTO>> getGroupBalances(@PathVariable Long groupId){
        return ResponseEntity.ok(expenseService.calculateGroupBalances(groupId));
    }

}
