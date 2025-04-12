package com.sharedexp.expense_service.service;

import com.sharedexp.GroupCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.sharedexp.expense_service.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class GroupEventConsumer {
    private final ExpenseRepository expenseRepository;

    @KafkaListener(topics = "group-events", groupId = "expense-group",
            containerFactory = "groupKafkaListenerFactory")
    public void consumeGroupEvent(com.sharedexp.GroupCreatedEvent event){
        System.out.println("Received group event: " + event);
    }
}
