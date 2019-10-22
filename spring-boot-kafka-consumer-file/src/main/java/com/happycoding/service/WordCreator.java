package com.happycoding.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class WordCreator {

	public void createWordDoc(List<String> list) {
		XWPFDocument document = null;
		if (!list.isEmpty()) {
			String fileName = "D:\\kafka-consumer\\" + list.get(0);
			try (OutputStream fileOut = new FileOutputStream(fileName)) {
				document = new XWPFDocument();
				for (int i = 1; i < list.size(); i++) {
					document.createParagraph().createRun().setText(list.get(i));
				}
				document.write(fileOut);
				System.out.println("File created: " + fileName);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// new WordCreator().processWordDoc(1);
	}
}
