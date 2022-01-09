package com.emicalculator.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.emicalculator.utils.FileIO;

import junit.framework.Assert;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	public static String browser_choice;
	public static String timestamp;

	public BaseUI() {
		prop = FileIO.initProperties();
	}

	/************** Invoke Browser ****************/
	public static WebDriver invokeBrowser() {
		System.out.println("Invoking");
		browser_choice = prop.getProperty("browserName");
		try {
			if (browser_choice.equalsIgnoreCase("firefox")) {
				driver = DriverSetup.getFirefoxDriver();
			} else if (browser_choice.equalsIgnoreCase("msedge")) {
				driver = DriverSetup.getMSEdgeDriver();
			} else if (browser_choice.equalsIgnoreCase("chrome")){
				driver = DriverSetup.getChromeDriver();
			} else{
				throw new Exception("Invalid browser name provided in property file");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		return driver;
	}

	/************** Get browser option from user ****************/
	public static int getBrowserOption() {
		int choice = 1;
		System.out
				.println("Browser options\n1 - Chrome\n2 - MS Edge \n3 - Firefox\nEnter choice: ");
		Scanner sc = new Scanner(System.in);
		
		choice = sc.nextInt();
		while (choice != 1 && choice != 2 && choice != 3) {
			System.out.println("Invalid choice entered.");
			System.out
					.println("Browser options\n1 - Chrome\n2 - MS Edge \n3 - Firefox\nEnter choice: ");
			choice = sc.nextInt();
		}
		sc.close();
		return choice;
	}

	/************** Open website URL ****************/
	public static void openBrowser(String websiteUrlKey) {
		try {
			System.out.println("Opening browser...");
			driver.get(prop.getProperty(websiteUrlKey));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/************** Switch to new tab ****************/
	public static void switchToNewTab() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(
			driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabs.size() - 1));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Switch to prev tab ****************/
	public static void switchToPrevTab() {
		try {
			ArrayList<String> tabs = new ArrayList<String>(
			driver.getWindowHandles());
			driver.close();
			driver.switchTo().window(tabs.get(tabs.size() - 2));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Get list of web elements ****************/
	public static List<WebElement> getListOfElements(By locator) {
		List<WebElement> list = null;
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		list = driver.findElements(locator);
		return list;
	}

	/************** Check if an element is present ****************/
	public static boolean isElementPresent(By locator, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/************** Send text to an element ****************/
	public static void sendText(By locator, String text) {
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).sendKeys(text);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	
	/************** Send keys and text to an element ****************/
	public static void sendKeys(By locator, String text) {
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator));
			driver.findElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"),text );
			driver.findElement(locator).sendKeys(Keys.TAB);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Get text of element ****************/
	public static String getText(By locator) {
		String text = null;
		try {
			new WebDriverWait(driver, 50).until(ExpectedConditions.presenceOfElementLocated(locator));
			text = driver.findElement(locator).getText();
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		return text;
	}

	/************** Click on element with WebElement ****************/
	public static void clickOn(By locator, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions
					.elementToBeClickable(locator));
			driver.findElement(locator).click();
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Click on element with Actions ****************/
	public static void clickAction(By locator, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions
					.elementToBeClickable(locator));
			Actions action = new Actions(driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(locator));
			jse.executeScript("window.scrollBy(0,-150)");
			action.moveToElement(driver.findElement(locator)).build().perform();
			action.click(driver.findElement(locator)).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Click on element with JavaScript ****************/
	public static void clickJS(By locator, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions
					.elementToBeClickable(locator));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(locator));
			jse.executeScript("arguments[0].click", driver.findElement(locator));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Switch to another frame ****************/
	public void switchToFrame(By locator) {
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(locator));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Wait for document to be in ready state ****************/
	public static void waitForDocumentReady(int timeout) {
		try {
			new WebDriverWait(driver, timeout)
					.until(webDriver -> ((JavascriptExecutor) webDriver)
							.executeScript("return document.readyState")
							.equals("complete"));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/**************** Get By locator using locator key ****************/
	public static By getLocator(String locatorKey) {
		if (locatorKey.endsWith("_id")) {
			return By.id(prop.getProperty(locatorKey));
		}
		if (locatorKey.endsWith("_name")) {
			return (By.name(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_className")) {
			return (By.className(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_xpath")) {
			return (By.xpath(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_css")) {
			return (By.cssSelector(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_linkText")) {
			return (By.linkText(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_partialLinkText")) {
			return (By.partialLinkText(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_tagName")) {
			return (By.tagName(prop.getProperty(locatorKey)));
		}
		reportFail("Failing test case, Invalid locator key: " + locatorKey);
		return null;
	}

	/************** Take screenshot ****************/
	public static void takeScreenShot(String filepath) {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File srcFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(filepath);
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/************** Report fail test ****************/
	public static void reportFail(String reportMessage) {
		Assert.fail("Test case failed: " + reportMessage);
	}

}

