package com.selenium.slx.mobileclient;

import com.thoughtworks.selenium.SeleneseTestNgHelper;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class ExpTestNG extends SeleneseTestNgHelper {
	private Selenium selenium;
	public WebDriver driver = new FirefoxDriver();

	@Before
	public void setUp() throws Exception {
		
		String baseUrl = "http://107.21.243.158/mobile-builds/stable/latest/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testMobileDefect13091669() throws Exception {
		// SE Test: SETest-Defect_13091669
		// Desc: validates that where multi currency is enabled, on adding product to opportunity, 2 of the 3 'extended price' fields do not change value when the 1st one does
		// Pre-Run Steps: Logoff, then Log back in as 'Lee'...
		// Required Entity: Opportunity - Abbott WorldWide-Phase I
		// ====================================
		// Load: SLX Mobile Client URL into FF..
				selenium.open("http://107.21.243.158/mobile-builds/stable/latest/");
				
				// Validate: Page Title
				assertEquals("SalesLogix", selenium.getTitle());
				
				// Validate: Copyright Info...
				verifyEquals("© 2013 Sage Software, Inc. All rights reserved.", selenium.getText("XPath=.//*[@id='login']/span[1]"));
				verifyEquals("Mobile V2.1.0 / SalesLogix V8.0.0", selenium.getText("XPath=.//*[@id='login']/span[2]"));
				
				// Step: Input username (no password)...
				selenium.type("css=input[name=\"username\"]", "Lee");
				
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
		
		// 'Step: navigate to Home screen...
		selenium.open("#_home");
		// Step: navigate to Opportunities list view...
		selenium.click("XPath=.//*[@id='home']/div[3]/ul[2]/li[7]/div[2]/h3");
		// Step: perform a search of a target Opportunity...
		assertEquals("Opportunities", selenium.getText("XPath=.//*[@id='pageTitle']"));
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_SearchWidget_11']/div/div[1]/input");
		selenium.type("XPath=.//*[@id='Sage_Platform_Mobile_SearchWidget_11']/div/div[1]/input", "Advising Group Test Opp 100");
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_SearchWidget_11']/div/div[3]/button");
		assertTrue(selenium.isTextPresent("Advising Group Test Opp 100 (Advising Group)"));
		// Step: click the Opportunity list item for editing...
		selenium.click("XPath=.//*[@id='opportunity_list']/ul/li[2]/div/h3");
		verifyEquals("Advising Group Test Opp 100", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Step: click the Products link...
		selenium.click("XPath=.//*[@id='opportunity_detail']/div[2]/ul[2]/li[1]/a/span");
		verifyEquals("Products", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Step: click the '+' button to add a new Product...
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_MainToolbar_0']/button[1]");
		verifyEquals("Opportunity Product", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Step: click Product lookup button to select a product...
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_Fields_LookupField_14']/button");
		verifyEquals("Products", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Step: search and select a Product...
		selenium.type("XPath=.//*[@id='Sage_Platform_Mobile_SearchWidget_31']/div/div[1]/input", "Blackberry");
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_SearchWidget_31']/div/div[3]/button");
		Thread.sleep(5000);
		assertTrue(selenium.isTextPresent("BlackBerry"));
		selenium.click("XPath=.//*[@id='product_related']/ul/li/div/h3");
		Thread.sleep(5000);
		verifyEquals("Opportunity Product", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Verify: check the values of the product and price fields...
		verifyEquals("BlackBerry", selenium.getValue("XPath=.//*[@id='Sage_Platform_Mobile_Fields_LookupField_14']/input"));
		verifyEquals("399.00", selenium.getValue("XPath=.//*[@id='Mobile_SalesLogix_Fields_MultiCurrencyField_1']/input"));
		// Verify: check the value of the 1st extended price
		verifyEquals("399.00", selenium.getValue("XPath=.//*[@id='Mobile_SalesLogix_Fields_MultiCurrencyField_4']/input"));
		// Step: enter a discount field value...
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_Fields_DecimalField_1']/input");
		selenium.type("XPath=.//*[@id='Sage_Platform_Mobile_Fields_DecimalField_1']/input", "15.00");
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Fields_MultiCurrencyField_1']/label");
		// Verify: updated adjusprice fields...
		verifyEquals("339.15", selenium.getValue("XPath=.//*[@id='opportunityproduct_edit']/div[2]/fieldset[2]"));
		// Verify: updated extended price fields...
		verifyEquals("339.15", selenium.getValue("XPath=.//*[@id='Mobile_SalesLogix_Fields_MultiCurrencyField_4']/input"));
		// Step: click the Top Cancel button...
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_MainToolbar_0']/button[2]");
		// 'End: navigate back to Home...
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_MainToolbar_0']/button[3]");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
  
}
