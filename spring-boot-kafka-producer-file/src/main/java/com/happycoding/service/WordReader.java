package com.happycoding.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WordReader {

	public List<String> processWordDoc(String filename) {
		List<String> msgList = new ArrayList<String>();
		FileInputStream fis = null;
		XWPFDocument xdoc = null;
		try {
			fis = new FileInputStream(filename);
			xdoc = new XWPFDocument(OPCPackage.open(fis));
			
			// Reads files and returns the complete content of it
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			String text = extractor.getText();
			// System.out.println(text);
			msgList.add(text);

			// Reads Files by Para... line by line reading
			/*List<XWPFParagraph> paragraphList = xdoc.getParagraphs();
			if (!paragraphList.isEmpty()) {
				for (XWPFParagraph paragraph : paragraphList) {
					if (!StringUtils.isEmpty(paragraph.getText())) {
						msgList.add(paragraph.getText());
						System.out.println("Line:" + paragraph.getText());
					}
				}
			}*/
		} catch (InvalidFormatException | IOException ex) {
			ex.printStackTrace();
		}
		return msgList;
	}

	public static void main(String[] args) {
		// new WordReader().processWordDoc(1);
	}
}
