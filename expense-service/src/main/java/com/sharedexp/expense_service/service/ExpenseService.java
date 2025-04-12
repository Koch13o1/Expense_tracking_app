package com.sharedexp.expense_service.service;

import com.sharedexp.UserBalanceDTO;
import com.sharedexp.expense_service.model.Expense;
import com.sharedexp.expense_service.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenseByGroupId(Long groupId){
        return expenseRepository.findByGroupId(groupId);
    }

    public List<UserBalanceDTO> calculateGroupBalances(Long groupId){
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
        Map<Long, Double> balanceMap = new HashMap<>();

        for(Expense expense: expenses){
            double splitAmount = expense.getAmount();

            for (Long participant : expense.getParticipants()){
                balanceMap.put(participant, balanceMap.getOrDefault(participant, 0.0)-splitAmount);
            }

            balanceMap.put(expense.getPaidBy(), balanceMap.getOrDefault(expense.getPaidBy(), 0.0) + expense.getAmount());
        }
        return balanceMap.entrySet()
                .stream()
                .map(e -> new UserBalanceDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
