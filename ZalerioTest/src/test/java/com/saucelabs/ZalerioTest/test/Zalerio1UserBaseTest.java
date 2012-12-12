package com.saucelabs.ZalerioTest.test;




import static junit.framework.Assert.assertEquals;

import java.io.FileInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.ZalerioTest.config.*;
/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your
 * Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce
 * OnDemand</a>. *
 * 
 * @author Ross Rowe
 */
@RunWith(Parallelized.class)
public class Zalerio1UserBaseTest implements SauceOnDemandSessionIdProvider {

	
	private String browser;
	private String browserVersion;
	private String os;
	private String userid;
	private String password;
	private String sessionId;
	
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("cloudbees_zalzero", "15b88203-7a0f-4339-804a-fffb6abb2e3c");
	public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	public @Rule TestName testName= new TestName();
	protected WebDriver driver1;

	public Zalerio1UserBaseTest(String os, String browser, String version){ //,
		//	String userid, String password) {
		super();
		this.browser = browser;
		this.browserVersion = version;
		this.os = os;
	//	this.userid = userid;
	//	this.password = password;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Parameters
	public static LinkedList browsersStrings() throws Exception {
		LinkedList browsers = new LinkedList();
		String OS[] = { "Windows 2008","Windows 2008" ,"Mac 10.6","Windows 2008"};
		String browser[] = { "firefox","googlechrome","safari","opera"};
		String version[] = { "13"," ","10","5","11"};
	//	String userid[] = { "abhilashbhaduri@gmail.com"};//,
		//		"griffinsingh1@gmail.com" };// ,"rahulmu550@gmail.com","hemantkumer007@gmail.com","mahasingh50@gmail.com"};
	//	String password[] = { "16081989"};//, "griffinsingh1" };// ,"rahulmu550","hemantkumer007","mahasingh50"};
		for (int i = 0; i <=0; i++) {
			browsers.add(new String[] { OS[i], browser[i], version[i]
					});
		}
		return browsers;
	}

	@Before
	public void setUp() throws Exception {
		String url;
		//get data from testing.properties
		Properties values= new Properties();
		values.load(new FileInputStream("testing.properties"));
		String type =System.getenv("JOB_NAME");
		if(type.contains("Zalerio-Staging-Test"))
		{
		url=values.getProperty("Staging_url");
		userid=values.getProperty("user1_stag_username");
		password=values.getProperty("password");
		}	
		else
		{
			url=values.getProperty("Production_url");
			userid=values.getProperty("user1_prod_username");
			password=values.getProperty("password");
		}	
		
		    DesiredCapabilities capabilities1=null;
	        
	        if(browser.contains("firefox"))
	        {
	        	capabilities1=DesiredCapabilities.firefox();
	        }
	        else if(browser.contains("googlechrome"))
	        {
	        	capabilities1= DesiredCapabilities.chrome();
	        }
	        else if(browser.contains("safari"))
	        {
	        	capabilities1=DesiredCapabilities.safari();
	        }
	        else if(browser.contains("ie"))
	        {
	        	capabilities1=DesiredCapabilities.internetExplorer();
	        }
	        else if(browser.contains("opera"))
	        {
	        	capabilities1=DesiredCapabilities.opera();
	        }
	        capabilities1.setCapability("version", browserVersion);
	        capabilities1.setCapability("platform", os);
	        capabilities1.setCapability("name",  testName.getMethodName());
	       
	        
	        this.driver1 = new RemoteWebDriver(
	                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
	                capabilities1);
	        this.sessionId = ((RemoteWebDriver)driver1).getSessionId().toString();
	    
	        UserLogin.Olduserlogin(driver1, userid, password,url);
}

//	@Test
	public void gameEnterTest()
	{
		WebElement startButton = driver1.findElement(By.id("startButton"));
		assertEquals(startButton.isDisplayed(), true);

	}
	

	@After
	public void tearDown() throws Exception {
		UserLogin.logout(driver1);
		driver1.quit();
	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return sessionId;
	}

}
