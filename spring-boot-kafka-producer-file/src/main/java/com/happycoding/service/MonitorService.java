package com.happycoding.service;

import java.io.IOException;

public interface MonitorService {
	
	public void watchDirectory(String directoryUriPath, Integer partitionNumber) throws IOException, InterruptedException;
}
