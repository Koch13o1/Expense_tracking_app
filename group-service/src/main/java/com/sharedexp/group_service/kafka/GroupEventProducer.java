package com.sharedexp.group_service.kafka;

import com.sharedexp.GroupCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GroupEventProducer {
    private final KafkaTemplate<String, GroupCreatedEvent> kafkaTemplate;

    public GroupEventProducer(KafkaTemplate<String, GroupCreatedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGroupCreatedEvent(GroupCreatedEvent event){
        kafkaTemplate.send("group-events", event);
    }
}
