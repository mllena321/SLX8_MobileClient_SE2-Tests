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

public class SpeedSearch_Basic-Test {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://107.21.243.158/mobile-builds/stable/latest/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testSpeedSearch_Basic-() throws Exception {
		// SE Test: SETest-SpeedSearch
		// Desc: performs a Speedsearch from the Home screen...
		// Required Entity: N/A
		// ====================================
		// 'Step: navigate to Home screen...
		selenium.open("#_home");
		Thread.sleep(5000);
		// Step: perform a search of a target Opportunity...
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_SpeedSearchWidget_0']/div/div[1]/input");
		selenium.type("XPath=.//*[@id='Mobile_SalesLogix_SpeedSearchWidget_0']/div/div[1]/input", "Abbott WorldWide-Phase I");
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_SpeedSearchWidget_0']/div/div[3]/button");
		Thread.sleep(5000);
		verifyEquals("Search Results", selenium.getText("XPath=.//*[@id='pageTitle']"));
		// VP: check that the save is successful and that Validation Summary error is not displayed...
		assertTrue(selenium.isTextPresent("(Abbott WorldWide)"));
		// End: navigate back to Home...
		selenium.click("XPath=.//*[@id='Mobile_SalesLogix_Views_MainToolbar_0']/button[2]");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
