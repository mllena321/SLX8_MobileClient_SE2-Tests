package com.selenium.slx.mobileclient;


import org.openqa.selenium.Alert;

import com.thoughtworks.selenium.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.After;
import org.junit.Before;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;

public abstract class BaseMobileTest extends SeleneseTestNgHelper {
	public WebDriver driver = new FirefoxDriver();
	public Selenium selenium;
	public String baseUrl;
	public String mobileUrl;
	public String startPage;
	public String userName;
	public String userPwd;
	public String copyrightLabel;
	public String versionLabel;
	public boolean acceptNextAlert = true;
	public StringBuffer verificationErrors = new StringBuffer();

	@Before	
	public void setUp() throws Exception {
		//WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		loadSettings();
		selenium = new WebDriverBackedSelenium(driver, baseUrl + mobileUrl);
	}
	
	public void loadSettings() throws Exception {
		Properties p = new Properties();
		FileReader reader = new FileReader("app.properties");
		p.load(reader);
		reader.close();
		baseUrl = p.getProperty("base_url");
		mobileUrl = p.getProperty("mobile_url");
		startPage = p.getProperty("start_page");
		userName = p.getProperty("user_name");
		userPwd = p.getProperty("user_pwd");
		copyrightLabel = p.getProperty("copyright_lbl");
		versionLabel = p.getProperty("version_lbl");
	}

	//@Test
	public void runTests() throws Exception {
		testBody();
	}
	
	public abstract void testBody() throws Exception;

	@After
	public void tearDown() throws Exception {
		selenium.stop();
		
		/*
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		*/
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alert.getText();
		} finally {
			acceptNextAlert = true;
		}
	}
	
	public void doLogin(String userName) throws Exception {
		// Load: SLX Mobile Client URL into FF..
		selenium.open(startPage);
		
		// Validate: Page Title
		assertEquals("SalesLogix", selenium.getTitle());
		
		// Validate: Copyright Info...
		verifyEquals("© 2013 Sage Software, Inc. All rights reserved.", selenium.getText("XPath=.//*[@id='login']/span[1]"));
		verifyEquals("Mobile V2.1.0 / SalesLogix V8.0.0", selenium.getText("XPath=.//*[@id='login']/span[2]"));
		
		// Step: Input username (no password)...
		selenium.type("css=input[name=\"username\"]", userName);
		
		// Step: Set remember option to ON...
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_Fields_BooleanField_0']/div/span[1]");
		
		// Step: Click Log On button to login...
		selenium.click("css=button.button.actionButton");
		Thread.sleep(7000);
		
		// Verify: Home Page is loaded...
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("Home".equals(selenium.getText("XPath=.//*[@id='pageTitle']"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
	}

	public void lookupItem(String itemType, String itemName) throws Exception {
		String lookupFldID = null;
		String lookupBtnID = null;
		String resultsLstID = null;
		
		clickHomeScreenLink(itemType, itemType);
		
		switch (itemType){
		case "My Activities":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_26']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_26']/div/div[3]/button";
			resultsLstID = ".//*[@id='myactivity_list']";
		case "Notes/History":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_27']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_27']/div/div[3]/input";
			resultsLstID = ".//*[@id='history_list']";
		case "Accounts":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_3']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_3']/div/div[3]/button";
			resultsLstID = ".//*[@id='account_list']";
		case "Contacts":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_3']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_3']/div/div[3]/button";
			resultsLstID = ".//*[@id='contact_list']";
		case "Leads":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_16']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_16']/div/div[3]/input";
			resultsLstID = ".//*[@id='lead_list']";
		case "Opportunities":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_11']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_11']/div/div[3]/input";
			resultsLstID = ".//*[@id='opportunity_list']";
		case "Tickets":
			lookupFldID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_18']/div/div[1]/input";
			lookupBtnID = ".//*[@id='Sage_Platform_Mobile_SearchWidget_18']/div/div[3]/input";
			resultsLstID = ".//*[@id='ticket_list']";
		}
		
		selenium.typeKeys("XPath=" + lookupFldID, itemName);
		selenium.click("XPath=" + lookupBtnID);
		Thread.sleep(5000);
		
		//wait for results to show
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("XPath=.//*[@id='" + resultsLstID + "']/ul/li[1]")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
		
		//check if search text exists in Speed Search results
		Assert.assertTrue(selenium.isTextPresent(itemName));
	}
	
	public void clickHomeScreenLink(String linkName, String pagetitle) throws Exception {
		String linkpath = "XPath=.//*[@id='home']/descendant::*[text()='" + linkName + "']";
		
		selenium.click(linkpath);
		Thread.sleep(7000);
		//test page title check
		verifyEquals(pagetitle, selenium.getText("XPath=.//*[@id='pageTitle']"));	
	}
		
	public void doLogout() throws Exception {
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_FooterToolbar_0']/div/button[4]");
		Thread.sleep(3000);
		//test page title check
		verifyEquals("Sage SalesLogix", selenium.getText("XPath=.//*[@id='pageTitle']"));
	}
	
	public void doSpeedSearch(String searchitem) throws Exception {
		selenium.open("#home");
		Thread.sleep(5000);
		
		//test page title check
		verifyEquals("Home", selenium.getText("XPath=.//*[@id='pageTitle']"));
		
		//enter text into the Speed Search input field
		selenium.typeKeys("XPath=.//*[@id='Mobile_SalesLogix_SpeedSearchWidget_0']/div/div[1]/input", searchitem);
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_SpeedSearchWidget_0']/div/div[3]/button");
		Thread.sleep(5000);
		
		//wait for results to show
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (selenium.isElementPresent("XPath=.//*[@id='speedsearch_list']/ul/li[1]")) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
		
		//check if search text exists in Speed Search results
		Assert.assertTrue(selenium.isTextPresent(searchitem));
		verifyEquals("Search Results", selenium.getText("XPath=.//*[@id='pageTitle']"));
	}
	
	public void makeSpeedSearchSelection(String linkitemtxt) throws Exception {
		selenium.click("XPath=/descendant::*[text() = '" + linkitemtxt + "']");
		Thread.sleep(5000);
	}

}
