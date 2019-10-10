package com.happycoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.happycoding.model.Provider;

@Service
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, Provider> kafkaTemplate;

	@Value("${topic.id}")
	private String topicId;

	@Async
	public void send(String message, String fileName, Integer value) {
		System.out.println("Current thread used for sending message to topic :" + Thread.currentThread().getName());
		kafkaTemplate.send(topicId, fileName, new Provider(fileName, value, message));
		System.out.println("Message sent to the Kafka Topic '" + topicId + "' Successfully");
	}
	
	
	@Async
	public void send(String message, String fileName, Integer value, String keyValue) {
		System.out.println("Current thread used for sending message to topic :" + Thread.currentThread().getName());
		kafkaTemplate.send(topicId, keyValue, new Provider(fileName, value, message));
		System.out.println("Message sent to the Kafka Topic '" + topicId + "' Successfully");
	}
}
