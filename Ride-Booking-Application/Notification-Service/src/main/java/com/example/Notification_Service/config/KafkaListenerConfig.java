package com.example.Notification_Service.config;


import com.example.Notification_Service.dto.request.StartNotificationRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaListenerConfig {


    @Bean
    public ConsumerFactory<String, StartNotificationRequest> startNotificationRequestConsumerFactory(){
        JsonDeserializer<StartNotificationRequest> json = new JsonDeserializer<>(StartNotificationRequest.class);

        json.setUseTypeHeaders(false);
        json.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                stringObjectMap(),
                new StringDeserializer(),
                json
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StartNotificationRequest> concurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,StartNotificationRequest>  facory =
                new ConcurrentKafkaListenerContainerFactory<>();
        facory.setConsumerFactory(startNotificationRequestConsumerFactory());
        return facory;
    }


    @Bean
    public Map<String,Object> stringObjectMap(){
        Map<String , Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "notifications");

        return  map;
    }
}
