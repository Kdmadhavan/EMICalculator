package com.emicalculator.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.emicalculator.base.BaseUI;
import com.emicalculator.pages.HomePage;
import com.emicalculator.utils.FileIO;

public class HomePageTest extends BaseUI {

	private WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		invokeBrowser();
		openBrowser("websiteURL");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("completed");
		//driver.close();
	}

	
/*	@Test(priority = 2)
	public void homeLoan() throws InterruptedException {
		System.out.println("-----------Home Loan-------");
		HomePage homePage = new HomePage(driver);
		//homePage.calculateEMI("350000","10.5","15");
		homePage.getResults() ;
	}
	
	@Test(priority = 3)
	public void personalLoan() throws InterruptedException {
		System.out.println("-----------Personal Loan-------");
		HomePage homePage = new HomePage(driver);
		
		By personalLoan_link = getLocator("personalLoan_linkText");
		clickOn(personalLoan_link, 0);
	
		//homePage.calculateEMI("450000","15.5","20");
		homePage.getResults() ; 
	}
	
	@Test(priority = 4)
	public void carLoan() throws InterruptedException {
		System.out.println("-----------Car Loan-------");
		HomePage homePage = new HomePage(driver);
		By carLoan_link = getLocator("carLoan_linkText");
		clickOn(carLoan_link, 0);
		//homePage.calculateEMI("150000","8","5");
		homePage.getResults() ; n
	}
	*/
	/******************************************************
	 ******** Data provider for Home Loan Data ******** 
	 ******************************************************/
	@DataProvider
	public Object[][] homeLoanData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("HomeLoan");
		int noRow = dataMap.size();
		int noCol = dataMap.get("1").size();
		Object[][] data = new Object[noRow][noCol];
		for (int i = 0; i < noRow; ++i) {
			ArrayList<String> rowData = dataMap.get("" + (i + 1));
			for (int j = 0; j < noCol; ++j) {
				data[i][j] = rowData.get(j);
				System.out.println("data from excel for home loan=="+rowData.get(j));
			}
		}
		return data;
	}
	
	/******************************************************************
	 ************* EMI Calculation test for home loan *****************
	 ******************************************************************/
	@Test(dataProvider = "homeLoanData", priority = 1)
	public void homeLoanDataTest(String amount, String rate, String tenure) {
		System.out.println("-----------Home Loan-------");
		HomePage homePage = new HomePage(driver);
		waitForDocumentReady(60);
		homePage.calculateEMI(amount,rate,tenure);
		String data[] = homePage.getResults();
		FileIO.writeExcel("HomeLoan",data);
	}
	
	/******************************************************
	 ******** Data provider for Personal Loan Data ******** 
	 ******************************************************/
	@DataProvider
	public Object[][] personalLoanData() throws IOException {
		
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("PersonalLoan");
		int noRow = dataMap.size();
		int noCol = dataMap.get("1").size();
		Object[][] data = new Object[noRow][noCol];
		for (int i = 0; i < noRow; ++i) {
			ArrayList<String> rowData = dataMap.get("" + (i + 1));
			for (int j = 0; j < noCol; ++j) {
				data[i][j] = rowData.get(j);
				System.out.println("data from excel for personal loan=="+rowData.get(j));
			}
		}
		return data;
	}
	
	/******************************************************************
	 ***************EMI Calculation test for personal loan*************
	 ******************************************************************/
	@Test(dataProvider = "personalLoanData", priority = 2)
	public void personalLoanDataTest(String amount, String rate, String tenure) {
		System.out.println("-----------Personal Loan-------");
		HomePage homePage = new HomePage(driver);
		By personalLoan_link = getLocator("personalLoan_linkText");
		clickOn(personalLoan_link, 0);
	
		waitForDocumentReady(60);
		homePage.calculateEMI(amount,rate,tenure);
//		/homePage.getResults() ;
	}
	
	/******************************************************
	 ******** Data provider for Car Loan Data ******** 
	 ******************************************************/
	@DataProvider
	public Object[][] carLoanData() throws IOException {
		HashMap<String, ArrayList<String>> dataMap = FileIO.readExcelData("CarLoan");
		int noRow = dataMap.size();
		int noCol = dataMap.get("1").size();
		Object[][] data = new Object[noRow][noCol];
		for (int i = 0; i < noRow; ++i) {
			ArrayList<String> rowData = dataMap.get("" + (i + 1));
			for (int j = 0; j < noCol; ++j) {
				data[i][j] = rowData.get(j);
				System.out.println("data from excel for car loan data=="+rowData.get(j));
			}
		}
		return data;
	}
	
	/******************************************************************
	 ************EMI Calculation test for car loan*********************
	 ******************************************************************/
	@Test(dataProvider = "carLoanData", priority = 3)
	public void carLoanDataTest(String amount, String rate, String tenure) {
		HomePage homePage = new HomePage(driver);
		System.out.println("-----------Car Loan-------");
		By carLoan_link = getLocator("carLoan_linkText");
		clickOn(carLoan_link, 0);
		//switchToNewTab();
		waitForDocumentReady(60);
		homePage.calculateEMI(amount,rate,tenure);
		//homePage.getResults() ;
	}
	
}
