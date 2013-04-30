package com.selenium.slx.mobileclient;

import com.thoughtworks.selenium.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LoginNonAdmin extends SeleneseTestNgHelper {
	private Selenium selenium;
	public String baseUrl;
	public String mobileUrl;
	public String startPage;
	public String userName;
	public String userPwd;

	@Before	
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
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
	}

	@Test
	public void testLoginNonAdmin() throws Exception {
		// Load: SLX Mobile Client URL into FF..		
		doLogin(userName);
		
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
		
		//test nav		
		clickHomeScreenLink("My Activities", "My Activities");
		
		//test logout		
		doLogout();
		
		// VP: Copyright Info...
		verifyEquals("Â© 2013 Sage Software, Inc. All rights reserved.", selenium.getText("XPath=.//*[@id='login']/span[1]"));
		verifyEquals("Mobile V2.1.0 / SalesLogix V8.0.0", selenium.getText("XPath=.//*[@id='login']/span[2]"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
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
		Thread.sleep(5000);
		
		// Verify: Home Page is loaded...
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("Home".equals(selenium.getText("XPath=.//*[@id='pageTitle']"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
	}
	
	public void clickHomeScreenLink(String linkName, String pagetitle) throws Exception {
		String linkpath = "XPath=.//*[@id='home']/descendant::*[text()='" + linkName + "']";
		selenium.click(linkpath);
		Thread.sleep(3000);
		//test page title check
		verifyEquals(pagetitle, selenium.getText("XPath=.//*[@id='pageTitle']"));	
	}
	
	public void doLogout() throws Exception {
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_FooterToolbar_0']/div/button[4]");
		Thread.sleep(3000);
		//test page title check
		verifyEquals("Sage SalesLogix", selenium.getText("XPath=.//*[@id='pageTitle']"));
	}
	
}
