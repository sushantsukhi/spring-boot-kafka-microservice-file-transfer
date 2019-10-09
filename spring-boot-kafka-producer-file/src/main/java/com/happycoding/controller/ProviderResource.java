package com.happycoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happycoding.service.KafkaProducer;
import com.happycoding.service.WordReader;

@RestController
public class ProviderResource {

	@Autowired
	KafkaProducer kafkaProducer;

	@Autowired
	WordReader wordReader;

	@GetMapping(value = "kafka-example")
	public String producer(@RequestParam("value") int value) {
		/*int fileNo = value % 5;
		List<String> processWordDocMsgs = wordReader.processWordDoc(fileNo);
		for (String msg : processWordDocMsgs) {
			kafkaProducer.send(msg, ""+fileNo, value);
		}*/
		return "Message sent to the Kafka Topic java_in_use_topic Successfully";
	}

}