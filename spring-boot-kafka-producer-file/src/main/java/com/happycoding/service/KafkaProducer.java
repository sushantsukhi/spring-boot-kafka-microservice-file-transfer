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

	@Async("taskExecutor")
	public void send(String message, String fileName, Integer value) {
		System.out.println( Thread.currentThread().getName() + " Thread used for sending message: " + fileName);
		kafkaTemplate.send(topicId, fileName, new Provider(fileName, value, message));
		System.out.println(fileName + " sent to the Kafka Topic '" + topicId + "' Successfully");
	}
	
	
	@Async("taskExecutor")
	public void send(String message, String fileName, Integer value, String keyValue, Integer partitionNumber) {
		System.out.println( Thread.currentThread().getName() + " Thread used for sending message: " + fileName);
		kafkaTemplate.send(topicId, partitionNumber,  keyValue, new Provider(fileName, value, message));
		System.out.println(fileName + " sent to the Kafka Topic '" + topicId + "' Successfully");
	}
}
