package com.sharedexp.settlement_service.service;

import com.sharedexp.SettlementTransactionDTO;
import com.sharedexp.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final WebClient.Builder webClientBuilder;

    public List<SettlementTransactionDTO> calculateSettlements(Long groupId){
        List<ExpenseDTO> expenses = Arrays.asList(
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8083/expenses/" + groupId)
                        .retrieve()
                        .bodyToMono(ExpenseDTO.class)
                        .block()
        );
        Map<String, Double> balanceMap = new HashMap<>();

        for(ExpenseDTO expense: expenses){
            double splitAmount = expense.getAmount()/////////////////////////'
        }
    }
}
