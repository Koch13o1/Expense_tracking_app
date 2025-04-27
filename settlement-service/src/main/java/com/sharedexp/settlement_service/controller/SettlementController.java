package com.sharedexp.settlement_service.controller;

import com.sharedexp.SettlementTransactionDTO;
import com.sharedexp.settlement_service.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/{groupId}")
    public List<SettlementTransactionDTO> getSettlements(@PathVariable Long groupId){
        return settlementService.calculateSettlements(groupId);
    }
}
