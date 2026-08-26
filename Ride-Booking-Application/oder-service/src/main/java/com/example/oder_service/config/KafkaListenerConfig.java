package com.example.oder_service.config;

import com.example.oder_service.dto.CreateOrderReq;
import com.example.oder_service.dto.UpdateStatusRequest;
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
    public ConsumerFactory<String, CreateOrderReq> createOrderReqConsumerFactory(){
        JsonDeserializer<CreateOrderReq> deserializer =
                new JsonDeserializer<>(CreateOrderReq.class);

        deserializer.setUseTypeHeaders(false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                stringObjectMap(),
                new StringDeserializer(),
                deserializer
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreateOrderReq>
    createOrderKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, CreateOrderReq> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(createOrderReqConsumerFactory());

        return factory;
    }


    @Bean
    public ConsumerFactory<String, UpdateStatusRequest> updateStatusRequestConsumerFactory(){
        JsonDeserializer<UpdateStatusRequest> update = new JsonDeserializer<>(UpdateStatusRequest.class);

        update.addTrustedPackages("*");
        update.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                stringObjectMap(),
                new StringDeserializer(),
                update
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UpdateStatusRequest> updateStatusRequestKafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, UpdateStatusRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(updateStatusRequestConsumerFactory());

        return factory;

    }



    @Bean
    public Map<String, Object> stringObjectMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        map.put(ConsumerConfig.GROUP_ID_CONFIG, "create-group");
        map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return map;
    }
}
