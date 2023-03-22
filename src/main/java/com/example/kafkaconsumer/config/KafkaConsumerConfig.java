package com.example.kafkaconsumer.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  //config for string plain Test
  @Bean
  public ConsumerFactory<String,String> consumerFactory(){
    Map<String,Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
    config.put(ConsumerConfig.GROUP_ID_CONFIG,"KafkaConsumer1");
    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
    ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String,String>();
    kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
    return kafkaListenerContainerFactory;
  }


  //config for Json Data

  @Bean
  public ConsumerFactory<String,User> userConsumerFactory(){
    Map<String,Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    config.put(ConsumerConfig.GROUP_ID_CONFIG,"KafkaConsumer2");
    return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new org.springframework.kafka.support.serializer.JsonDeserializer<>(User.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String,User> userKafkaListenerContainerFactory(){
    ConcurrentKafkaListenerContainerFactory<String,User> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String,User>();
    kafkaListenerContainerFactory.setConsumerFactory(userConsumerFactory());
    return kafkaListenerContainerFactory;
  }

}
