package com.selenium.slx.mobileclient;

import org.junit.Test;


public class LoginNonAdminEXP extends BaseMobileTest {
	
	@Test
	public void doTest() throws Exception {
		runTests();
	}
	
	public void testBody() throws Exception {
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
		
		//test Speed Search + item nav
		//doSpeedSearch("Abbott");
		//makeSpeedSearchSelection("Hans Wurst (Abbot)");
		
		//test nav		
		//clickHomeScreenLink("My Activities", "My Activities");
		
		//tets item search
		lookupItem("Leads", "Best");
		
		/*
		//test logout		
		doLogout();
		
		// VP: Copyright Info...
		verifyEquals(copyrightLabel, selenium.getText("XPath=.//*[@id='login']/span[1]"));
		verifyEquals(versionLabel, selenium.getText("XPath=.//*[@id='login']/span[2]"));
		*/
	}
		
}
