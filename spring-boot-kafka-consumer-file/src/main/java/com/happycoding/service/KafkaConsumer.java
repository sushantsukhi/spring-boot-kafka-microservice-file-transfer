package com.happycoding.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.happycoding.model.Provider;

@Service
public class KafkaConsumer {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private WordCreator wordCreator;

	private Map<String, List<String>> map = new HashMap<String, List<String>>();

	@KafkaListener(topics = "#{'${topic.id}'}", containerFactory = "providerKafkaListenerContainerFactory")
	public void consumeJson(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Provider provider) {

		if (map.containsKey(key)) {
			if ("EOF".equals(provider.getName())) {
				wordCreator.createWordDoc(map.get(key));
				map.clear();
			} else {
				map.get(key).add(provider.getName());
			}
		} else {
			List<String> list = new ArrayList<String>();
			list.add(provider.getName());
			map.put(key, list);
		}
		System.out.println("Consumed JSON key:" + key);
		System.out.println("Consumed JSON partition:" + partition);
		System.out.println("Consumed JSON timestamp:" + ts);
		System.out.println("Consumed JSON topic:" + topic);
		// mongoTemplate.save(provider);
		System.out.println("Consumed JSON Message:" + provider);
	}
}