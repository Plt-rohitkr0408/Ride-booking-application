package com.example.Ride_Service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    /*
    @Bean
    public ProducerFactory<String, CreateOrderReq> producerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );
        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );
        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, CreateOrderReq> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    */

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder
                .name("create-order")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
