package com.happycoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MonitorDirectory {

	@Autowired
	MonitorService monitorService;

	@Value(value = "${directory.paths}")
	String directoryPaths;

	public void watchDirectory() {
		System.out.println("MonitorDirectory::watchDirectory started..");
		try {
			if(!StringUtils.isEmpty(directoryPaths) && directoryPaths.contains(",")) {
				int partitonNumber = 0;
				for(String path:directoryPaths.split(",")) {
					monitorService.watchDirectory(path, partitonNumber);
					partitonNumber++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("MonitorDirectory::watchDirectory ended..");
	}

	// public static void main(String[] args) throws IOException,
	// InterruptedException {
	// new MonitorDirectory().watchDirectory();
	// }
}
