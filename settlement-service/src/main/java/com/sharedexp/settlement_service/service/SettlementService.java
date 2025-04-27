package com.sharedexp.settlement_service.service;

import com.sharedexp.SettlementTransactionDTO;
import com.sharedexp.ExpenseDTO;
import com.sharedexp.UserBalanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.*;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final WebClient.Builder webClientBuilder;

    public List<SettlementTransactionDTO> calculateSettlements(Long groupId){
        List<ExpenseDTO> expenses = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8083/expenses/" + groupId)
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                response -> response.bodyToMono(String.class)
                                        .map(error -> new RuntimeException("Failed to fetch expenses: " + error)))
                        .bodyToFlux(ExpenseDTO.class)
                        .collectList()
                        .block();

        Map<Long, Double> balanceMap = new HashMap<>();

        if (expenses == null || expenses.isEmpty()) {
            throw new RuntimeException("No expenses found for groupId: " + groupId);
        }

        for(ExpenseDTO expense: expenses) {
            double splitAmount = expense.getAmount() / expense.getParticipants().size();
            balanceMap.put(expense.getPaidBy(), balanceMap.getOrDefault(expense.getPaidBy(), 0.0) + expense.getAmount());

            for(Long participantId: expense.getParticipants()){
                balanceMap.put(participantId, balanceMap.getOrDefault(participantId, 0.0) - splitAmount);
            }
        }

        return minimizeTransaction(balanceMap);
    }

    private List<SettlementTransactionDTO> minimizeTransaction(Map<Long, Double> balanceMap){
        PriorityQueue<UserBalanceDTO> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.getBalance(), a.getBalance()));
        PriorityQueue<UserBalanceDTO> debtors = new PriorityQueue<>((a, b) -> Double.compare(a.getBalance(), b.getBalance()));

        for (Map.Entry<Long, Double> entry: balanceMap.entrySet()){
            if (entry.getValue() > 0) {
                creditors.offer(new UserBalanceDTO(entry.getKey(), entry.getValue()));
            }else if(entry.getValue() < 0){
                debtors.offer(new UserBalanceDTO(entry.getKey(), entry.getValue()));
            }
        }

        List<SettlementTransactionDTO> transaction = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()){
            UserBalanceDTO creditor = creditors.poll();
            UserBalanceDTO debtor = debtors.poll();

            double settleAmount = Math.min(creditor.getBalance(), -debtor.getBalance());

            transaction.add(new SettlementTransactionDTO(debtor.getUserId(), creditor.getUserId(), settleAmount));

            creditor.setBalance(creditor.getBalance() - settleAmount);
            debtor.setBalance(debtor.getBalance() + settleAmount);

            if (creditor.getBalance() > 0) creditors.offer(creditor);
            if (debtor.getBalance() > 0) debtors.offer(debtor);
        }

        if (transaction.isEmpty()) {
            System.out.println("All balances are already settled. No transactions needed.");
        }

        return transaction;
    }

}
