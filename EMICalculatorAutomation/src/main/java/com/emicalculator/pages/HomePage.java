package com.emicalculator.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.emicalculator.base.BaseUI;

public class HomePage extends BaseUI {

	public WebDriver driver;
	
	
	By loanAmt_textbox = getLocator("loanamount_id");
	By interestRate_textbox = getLocator("loaninterest_id");
	By loanTenure_textbox = getLocator("loanterm_id");
	By loanEmi_label = getLocator("loanEmi_xpath");
	By interestPayable_label = getLocator("interestPayable_xpath");
	By totalPayment_label = getLocator("totalPayment_xpath");
	By totalInterest_label = getLocator("totalInterest_xpath");
	By principalLoanAmount_label = getLocator("principalLoanAmount_xpath");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}


	/************* Search for specified location **************/
	public void calculateEMI(String loanAmt,String interestRate, String loanTenure) {
		sendKeys(loanAmt_textbox, loanAmt);
		sendKeys(interestRate_textbox, interestRate);
		sendKeys(loanTenure_textbox, loanTenure);
	}
	
	
	public String[] getResults() {
		String loanEmi = getText(loanEmi_label);
		String intPayable = getText(interestPayable_label);
		String totalPayment =getText(totalPayment_label);
		String totalInterest =getText(totalInterest_label);
		String principalAmt =getText(principalLoanAmount_label);
		String[] data = { loanEmi,intPayable,totalPayment,totalInterest ,principalAmt};
		return data;
		
		
		
		
		//getHomeLoanEmiResults(data,cycle);
		
		
	}
	
	/*public void getHomeLoanEmiResults(String[] data,int cycle) {
		
		Std st[] = new Std[4];
	      //Populating the array
	      st[0] = new Std("Bala", 18);
	      st[1] = new Std("Rama", 17);
	      st[2] = new Std("Raju", 15);
	      st[3] = new Std("Raghav", 20);
		
		String[] hotelNames = holidayHomesPage.getHotelNames();
		String[] totalPrices = holidayHomesPage.getTotalPrices();
		String[] perNightPrices = holidayHomesPage.getPerNightPrices();
		String[][] data1 = new String[5][5];
		for (int i = 0; i < 5; ++i) {
			data[i][0] = hotelNames[i];
			data[i][1] = totalPrices[i];
			data[i][2] = perNightPrices[i];
		}
		
		
		String[][] result_values = new String[5][5];
		for (int i = 0; i < 5; i++) {
			result_values[i] = getText(loanEmi_label);
		}
		
		FileIO.writeExcel(result_values,"HomeLoan");
	}*/
	/*
	public String[] getPersonlLoanEmiResults() {
		String[] result_values = new String[5];
		for (int i = 0; i < 5; i++) {
			result_values[i] = getText(loanEmi_label);
		}
		return result_values;
	}
	
	public String[] getCarLoanEmiResults() {
		String[] result_values = new String[5];
		for (int i = 0; i < 5; i++) {
			result_values[i] = getText(loanEmi_label);
		}
		return result_values;
	}*/
	
	
	/*public void loan(String loanAmount,String iRate, String tenure) throws InterruptedException {
		WebElement loanAmt = driver.findElement(By.id("loanamount"));
		//loanAmt_textbox.sendKeys(Keys.chord(Keys.CONTROL, "a"),loanAmount );
		//loanAmt_textbox.sendKeys(Keys.TAB);
		
		WebElement ir = driver.findElement(By.id("loaninterest"));
		ir.sendKeys(Keys.chord(Keys.CONTROL, "a"), iRate);
		ir.sendKeys(Keys.TAB);
		
		WebElement term = driver.findElement(By.id("loanterm"));
		term.sendKeys(Keys.chord(Keys.CONTROL, "a"), tenure);
		term.sendKeys(Keys.TAB);
	}

	public void printResults()  {
		WebElement emi = driver.findElement(By.xpath("//div[@id=\"emiamount\"]/p/span"));
		System.out.println("Loan EMI ="+emi.getText());
		
		WebElement interestPayable = driver.findElement(By.xpath("//div[@id=\"emitotalinterest\"]/p/span"));
		System.out.println("Total Interest Payable ="+interestPayable.getText());
		
		WebElement totalPayment = driver.findElement(By.xpath("//div[@id=\"emitotalamount\"]/p/span"));
		System.out.println("Total Payment ="+totalPayment.getText());
		
		//WebElement totalInterest = driver.findElement(By.xpath("//*[@id=\"highcharts-jvht56r-207\"]/svg/g[2]/g[2]/text/tspan"));
		//System.out.println("Total Interest ="+totalInterest.getText());
		
		
		//WebElement principalLoanAmt = driver.findElement(By.xpath("//*[@id=\"highcharts-jvht56r-207\"]/svg/g[2]/g[1]/text/tspan"));
		//System.out.println("Principal Loan Amount ="+principalLoanAmt.getText());
		
	}*/
	
}


