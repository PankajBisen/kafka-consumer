package com.example.kafkaconsumer;

import com.example.kafkaconsumer.config.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaConsumerApplication {

	User userFromTopic=null;

	List<String> message = new ArrayList<>();

	@KafkaListener(groupId = "KafkaConsumer1",topics = "welcomeTopic",containerFactory = "kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic(String data){
		message.add(data);
		return message;
	}
	@KafkaListener(groupId = "KafkaConsumer2",topics = "welcomeTopic",containerFactory = "userKafkaListenerContainerFactory")
	public User getJsonMsgFromTopic(User user){
		userFromTopic=user;
		return userFromTopic;
	}

	@GetMapping("/consumerStringMsg")
	public List<String> consumeMsg(){
		return message;
	}

	@GetMapping("/consumerJsonMsg")
	public User consumeJsonMsg(){
		return userFromTopic;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
