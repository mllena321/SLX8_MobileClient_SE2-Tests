package com.example.tests;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class LoginNonAdmin {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://107.21.243.158/mobile-builds/stable/latest/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testLoginNonAdmin() throws Exception {
		// Load: SLX Mobile Client URL into FF..
		selenium.open("#_login");
		// Validate: Page Title
		assertEquals("SalesLogix", selenium.getTitle());
		// Validate: Copyright Info...
		verifyEquals("© 2013 Sage Software, Inc. All rights reserved.", selenium.getText("XPath=.//*[@id='login']/span[1]"));
		verifyEquals("Mobile V2.1.0 / SalesLogix V8.0.0", selenium.getText("XPath=.//*[@id='login']/span[2]"));
		// Step: Login as 'Admin' (no Password)...
		selenium.type("css=input[name=\"username\"]", "Lee");
		// Step: Set remember option to ON...
		selenium.click("XPath=.//*[@id='Sage_Platform_Mobile_Fields_BooleanField_0']/div/span[1]");
		// Step: Click Log On button to login...
		selenium.click("css=button.button.actionButton");
		// Verify: Home Page is loaded...
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("Home".equals(selenium.getText("XPath=.//*[@id='pageTitle']"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		verifyEquals("Home", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// Verify: Copyright Info...
		verifyEquals("© 2013 Sage Software, Inc. All rights reserved.", selenium.getText("XPath=.//*[@id='login']/span[1]"));
		verifyEquals("Mobile V2.1.0 / SalesLogix V8.0.0", selenium.getText("XPath=.//*[@id='login']/span[2]"));
		// Verify: order of Go To links...
		verifyEquals("Add Account/Contact", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[1]/li/div[2]/h3"));
		verifyEquals("My Activities", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[1]/div[2]/h3"));
		verifyEquals("Calendar", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[2]/div[2]/h3"));
		verifyEquals("Notes/History", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[3]/div[2]/h3"));
		verifyEquals("Accounts", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[4]/div[2]/h3"));
		verifyEquals("Contacts", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[5]/div[2]/h3"));
		verifyEquals("Leads", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[6]/div[2]/h3"));
		verifyEquals("Opportunities", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[7]/div[2]/h3"));
		verifyEquals("Tickets", selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[8]/div[2]/h3"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
