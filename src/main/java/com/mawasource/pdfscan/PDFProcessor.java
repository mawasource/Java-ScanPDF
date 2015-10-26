package com.mawasource.pdfscan;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;

public class PDFProcessor {

	private static final String REGION_NUMBERS = "numbers";
	
	private final Rectangle rectNumber = new Rectangle(160, 240, 90, 30);

	private final Map<Integer, Integer> numberMap = new HashMap<Integer, Integer>();
	private final ValueComparator valueComparator = new ValueComparator(numberMap);
	private final Map<Integer, Integer> sortedValues = new TreeMap<Integer, Integer>(valueComparator);

	private PDDocument document = null;
	private String path;
	private File inputFile;
	
	public PDFProcessor() {
	}
	
	public void process(String path) {
		this.path = path;
		inputFile = new File(path);
		try {
			loadDocument();
			processDocument();
			createNewDoc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processDocument() throws IOException {
		if (document != null) {
			final List allPages = document.getDocumentCatalog().getAllPages();
			final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			stripper.setSortByPosition(true);
			stripper.addRegion(REGION_NUMBERS, rectNumber);
			String numbers;
			PDPage page;
			for (int i = 0; i < allPages.size(); i++) {
				page = (PDPage) allPages.get(i);
				stripper.extractRegions(page);
				numbers = stripper.getTextForRegion(REGION_NUMBERS).replaceAll("\\D+", "");
				numberMap.put(i, Integer.valueOf(numbers));
				// System.out.println("Processing page: " + i);
			}
			sortedValues.putAll(numberMap);
			Utils.printMap(sortedValues);

		}
	}

	private void createNewDoc() throws IOException, COSVisitorException {
		PDDocument newDoc = new PDDocument();
		try {
			newDoc = PDDocument.load(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List allPagestmp = newDoc.getDocumentCatalog().getAllPages();
		for (int k = 0; k < allPagestmp.size(); k++) {
			newDoc.removePage(0);
		}
		int x = 0;
		for (Map.Entry<Integer, Integer> entry : sortedValues.entrySet()) {
			newDoc.addPage((PDPage) allPagestmp.get(entry.getKey()));
			x++;
		}
		System.out.print(x);
		newDoc.save(path.replace(".pdf", Constants.NEW_FILE_POSTFIX + ".pdf"));
		newDoc.close();
	}

	private void loadDocument() {
		try {
			document = PDDocument.load(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
