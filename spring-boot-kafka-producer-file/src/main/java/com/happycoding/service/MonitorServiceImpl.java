package com.happycoding.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl implements MonitorService {

	@Autowired
	KafkaProducer kafkaProducer;
	@Autowired
	WordReader wordReader;

	private static final String EVEN_KEY = "EVEN######@%2";
	private static final String ODD_KEY = "ODD######@i%2!0";

	@Override
	@Async("dirExecutor")
	public void watchDirectory(String directoryUriPath,  Integer partitionNumber) throws IOException, InterruptedException {
		System.out.println("MonitorServiceImpl::watchDirectory started..");
		System.out.println( Thread.currentThread().getName() + " Thread used for sending message partition: " + partitionNumber
				+ " Directory path :" + directoryUriPath);
		Path faxFolder = Paths.get(directoryUriPath);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		boolean valid = true;
		int fileCounter = 0;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent<?> event : watchKey.pollEvents()) {
				// Kind<?> kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File processing: " + fileName);
					String fullPath = directoryUriPath + fileName;
					List<String> processWordDocMsgs = wordReader.processWordDoc(fullPath);
					if (!processWordDocMsgs.isEmpty()) {
						// Commenting below as sending complete file as single message to Kafka-Topic
						int no = 1;
						// kafkaProducer.send(fileName, fileName, no++);
						for (String msg : processWordDocMsgs) {
							// send message to specific partition on topic in odd-even fashion
							if (fileCounter % 2 == 0)
								kafkaProducer.send(msg, fileName, no++, EVEN_KEY, partitionNumber);
							else
								kafkaProducer.send(msg, fileName, no++, ODD_KEY, partitionNumber);
							fileCounter++;
						}
						// kafkaProducer.send("EOF", fileName, no++);
					}
				}
			}
			valid = watchKey.reset();
		} while (valid);
		System.out.println("MonitorServiceImpl::watchDirectory ended..");
	}
	
}
