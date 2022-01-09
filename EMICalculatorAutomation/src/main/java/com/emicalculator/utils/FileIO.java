package com.emicalculator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emicalculator.base.BaseUI;

public class FileIO {

	private static FileInputStream read_file;
	private static XSSFWorkbook workbook;
	private static XSSFSheet worksheet;
	private static Row row;
	private static FileOutputStream write_file;
	private static File file;
	private static Properties prop;

	/************** Get properties file ****************/
	public static Properties initProperties() {
		if (prop == null) {
			prop = new Properties();
			String userDir = System.getProperty("user.dir");
			try {
				FileInputStream file = new FileInputStream(
						userDir + "/src/test/resources/objectrepository/config.properties");
				// file.available();
				prop.load(file);
				System.out.println("Prop file loaded");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop;
	}


	/**************
	 * Get Test Data from excel sheet based on Test Name
	 ****************/
	public static HashMap<String, ArrayList<String>> readExcelData(String testName) throws IOException {

		// Creating hashmap to store data
		HashMap<String, ArrayList<String>> data = new HashMap<>();

		// Reading the excel sheet
		read_file = new FileInputStream(System.getProperty("user.dir") + prop.getProperty("testData_path"));
		workbook = new XSSFWorkbook(read_file);
		worksheet = workbook.getSheet(testName);

		// Iterating over all cells in the sheet
		Iterator<Row> rowIterator;
		ArrayList<String> rowData = new ArrayList<>();
		rowIterator = worksheet.iterator();
		int rowNum = 1;// "1": {..}
		if (rowIterator.hasNext())
			row = rowIterator.next();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			Iterator<Cell> cellIterator = row.iterator();
			if (row.getCell(0) == null) {
				break;
			}

			// Writing cell data to hashmap based on cell data type
			rowData = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				CellType type = cell.getCellTypeEnum();
				if (type.equals(CellType.STRING))
					rowData.add(cell.getStringCellValue());
				else if (type.equals(CellType.NUMERIC))
					rowData.add("" + (int) cell.getNumericCellValue());
			}
			data.put("" + (rowNum), rowData);
			rowNum++;
		}

		// Closing FileInputStream and XSSFWorkbook
		workbook.close();
		read_file.close();
		return data;
	}

	/**************
	 * Write a single dimensional array to excel sheet
	 ****************/
	/*
	 * public static void writeExcel(String sheetName, String heading) { String[][]
	 * newData = new String[data.length][1]; String[] headings = new String[1];
	 * headings[0] = heading; for (int i = 0; i < data.length; ++i) { newData[i][0]
	 * = data[i]; } writeExcel(sheetName, headings); }
	 */

	/**************
	 * Write a double dimensional array to excel sheet
	 ****************/
	public static void writeExcel(String sheetName,String[] data) {
		String filePath = System.getProperty("user.dir") + prop.getProperty("testData_path");
		System.out.println("----Writing to excel----");
		file = new File(filePath);
		boolean fileExists = false;
		try {

			// Checking if output excel file already exists
			if (file.isFile()) {

				// Read existing excel workbook
				read_file = new FileInputStream(file);
				workbook = new XSSFWorkbook(read_file);
				fileExists = true;
			} else {

				// Create new excel workbook
				workbook = new XSSFWorkbook();
			}
			int rowStart = 0;

			// Checking if sheet already exists
			if (workbook.getSheet(sheetName) == null)

				// Creating new sheet, setting row start to first cell
				worksheet = workbook.createSheet(sheetName);
			else {

				// Getting required sheet, setting row to last empty cell
				worksheet = workbook.getSheet(sheetName);
				rowStart = worksheet.getLastRowNum();
				System.out.println("rowStart == " + rowStart);
				// int lastRow = sheet.getLastRowNum();
				Row row;
				for (int j = 3; j <= 7; j++) {
				for (int i = 0; i < rowStart; i++) {
					System.out.println("data =="+data[i]);
					
						row = worksheet.getRow(i+1);
						Cell cell = row.createCell(j);
						cell.setCellValue(data[i]);
					}
				}

			}

			// Setting headings for the output data
			// row = worksheet.createRow(rowStart);
			/*
			 * for (int i = 3; i <rowStart; i++) { //Cell cell = row.createCell(i);
			 * //cell.setCellValue("Java"); row.createCell(i).setCellValue(headings[i]); }
			 */

			// Writing data to the sheet
			/*
			 * for (int i = 0; i < data.length; ++i) { row = worksheet.createRow(i
			 * +rowStart+ 1); for (int j = 0; j < data[0].length; ++j) {
			 * row.createCell(j).setCellValue(data[i][j]); } }
			 * 
			 * //Auto resize each column for (int i = 0; i < data[0].length; ++i) {
			 * worksheet.autoSizeColumn(i) ; }
			 */

			// Writing workbook to required file
			write_file = new FileOutputStream(filePath);
			workbook.write(write_file);

			// Closing FileInputStream, FileOutputStream and XSSFWorkbook
			write_file.close();
			workbook.close();
			if (fileExists)
				read_file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************** Write a double dimensional array to excel sheet ****************/
	public static void writeToExcel(String[][] data, String sheetName) {
		String filePath = System.getProperty("user.dir") + prop.getProperty("testData_path");
		file = new File(filePath);
		boolean fileExists = false;
		try {
			
			//Checking if output excel file already exists
			if (file.isFile()) {
				
				//Read existing excel workbook
				read_file = new FileInputStream(file);
				workbook = new XSSFWorkbook(read_file);
				fileExists = true;
			} else {
				
				//Create new excel workbook
				workbook = new XSSFWorkbook();
			}
			int rowStart = 0;
			
			//Checking if sheet already exists
			if (workbook.getSheet(sheetName) == null)
				
				//Creating new sheet, setting row start to first cell
				worksheet = workbook.createSheet(sheetName);
			else{
				
				//Getting required sheet, setting row to last empty cell
				worksheet = workbook.getSheet(sheetName);
				rowStart = worksheet.getLastRowNum()+2;
			}
			
			//Setting headings for the output data
			/*row = worksheet.createRow(rowStart);
			for (int i = 0; i < data[0].length; ++i) {
				row.createCell(i).setCellValue(headings[i]);
			}*/
			
			//Writing data to the sheet
			for (int i = 0; i < data.length; ++i) {
				row = worksheet.createRow(i +rowStart+ 1);
				for (int j = 3; j <= 7; j++) {
					row.createCell(j).setCellValue(data[i][j]);
				}
			}
			
			//Auto resize each column
			for (int i = 0; i < data[0].length; ++i) {
				worksheet.autoSizeColumn(i);
			}
			
			//Writing workbook to required file
			write_file = new FileOutputStream(filePath);
			workbook.write(write_file);
			
			//Closing FileInputStream, FileOutputStream and XSSFWorkbook
			write_file.close();
			workbook.close();
			if (fileExists)
				read_file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
