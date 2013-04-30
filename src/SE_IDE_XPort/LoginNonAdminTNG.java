package com.example.tests;

import com.thoughtworks.selenium.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.util.regex.Pattern;

public class LoginNonAdminTNG extends SeleneseTestNgHelper {
	@Test public void testLoginNonAdminTNG() throws Exception {
		// Load: SLX Mobile Client URL into FF..
		selenium.open("#_login");
		// Validate: Page Title
		assertEquals(selenium.getTitle(), "SalesLogix");
		// Validate: Copyright Info...
		verifyEquals(selenium.getText("XPath=.//*[@id='login']/span[1]"), "© 2013 Sage Software, Inc. All rights reserved.");
		verifyEquals(selenium.getText("XPath=.//*[@id='login']/span[2]"), "Mobile V2.1.0 / SalesLogix V8.0.0");
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

		verifyEquals(selenium.getText("XPath=.//*[@id='pageTitle']"), "Home");
		// Verify: Copyright Info...
		verifyEquals(selenium.getText("XPath=.//*[@id='login']/span[1]"), "© 2013 Sage Software, Inc. All rights reserved.");
		verifyEquals(selenium.getText("XPath=.//*[@id='login']/span[2]"), "Mobile V2.1.0 / SalesLogix V8.0.0");
		// Verify: order of Go To links...
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[1]/li/div[2]/h3"), "Add Account/Contact");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[1]/div[2]/h3"), "My Activities");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[2]/div[2]/h3"), "Calendar");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[3]/div[2]/h3"), "Notes/History");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[4]/div[2]/h3"), "Accounts");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[5]/div[2]/h3"), "Contacts");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[6]/div[2]/h3"), "Leads");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[7]/div[2]/h3"), "Opportunities");
		verifyEquals(selenium.getText("XPath=.//*[@id='home']/div[3]/ul[2]/li[8]/div[2]/h3"), "Tickets");
	}
}
