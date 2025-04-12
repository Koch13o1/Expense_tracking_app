package com.sharedexp.expense_service.config;


import com.sharedexp.GroupCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, GroupCreatedEvent> groupConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "expense-group");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow all packages
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, GroupCreatedEvent.class.getName());
        // System.out.println("## " + GroupCreatedEvent.class.getName());

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(), // Key deserializer
                new JsonDeserializer<>(GroupCreatedEvent.class) // Value deserializer
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GroupCreatedEvent> groupKafkaListenerFactory(){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, GroupCreatedEvent>();
        factory.setConsumerFactory(groupConsumerFactory());
        return factory;
    }
}
