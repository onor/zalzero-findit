package com.saucelabs.monitor;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 *
 * This test also includes the <a href="">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
 *
 * @author Ross Rowe
 */
public class WebDriverWithHelperTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("cloudbees_zalzero", "15b88203-7a0f-4339-804a-fffb6abb2e3c");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();

    private WebDriver driver;

    private String sessionId;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.chrome();
        capabillities.setCapability("version", " ");
        capabillities.setCapability("platform", Platform.XP);
        capabillities.setCapability("name",  testName.getMethodName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Test
    public void unionMonitorProd()
	{
    	Properties prop = new Properties();
    	String url=null,userid=null,userpwd=null;
    	try {
               //load a properties file
    		prop.load(new FileInputStream("environment.properties"));
 
               //get the property value and print it out
                 url=prop.getProperty("url");
    		userid=prop.getProperty("userid");
    		userpwd=prop.getProperty("userpwd");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		int status=0;
	//	System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver");
	//	WebDriver driver= new ChromeDriver();
	//	WebDriver driver=new FirefoxDriver();
	//	WebDriver driver=new OperaDriver();
		driver.get(url);
		WebElement login_form=driver.findElement(By.id("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys(userid);
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys(userpwd);
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//close tutorial in case of db cleanup
		try{
		WebElement popup_wrapper= driver.findElement(By.className("popup-wrapper"));
		WebElement start_popup=popup_wrapper.findElement(By.className("start-popup"));
		WebElement title=start_popup.findElement(By.className("title"));
		String Title=title.getText();
		assertEquals(Title,"Welcome to Zalerio!");
		WebElement left_button=start_popup.findElement(By.className("left-button"));
		left_button.click();
		}catch(Exception e)
		{
			System.out.println("tutorial not found");
		}
		//close pop up
		try{
		WebElement okButton = driver.findElement(By.className("msgbox-ok"));
		if (okButton != null) {
			System.out.println("ok button found");
		}
		okButton.click();
		}catch(Exception e)
		{}
		// close game end popup
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// If Game End Pop up Found then dismiss it
		try{
		WebElement score_friendpopup=driver.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));	
		close.click();
		}catch(Exception e)
		{e.printStackTrace();}
		//close feedback screen
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		try{
			WebElement rating_popup=driver.findElement(By.className("rating-popup"));
			WebElement close1= rating_popup.findElement(By.tagName("a"));
			if(close1.isDisplayed())
			{
				close1.click();
			}
		}catch(Exception e)
		{e.printStackTrace();}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		// verify username is visible
		WebElement leftHUD= driver.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
		WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
		WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
		WebElement userAreaName=userArea.findElement(By.id("currentUserName"));
		if(userAreaName.isDisplayed())
		{
			String name=userAreaName.getText();
			if(name.contains("Maria"))
			{
				status=1;
				System.out.println("name is "+name+" status "+status);
			}	
		}
		//verify right HUD game carousals
		
		WebElement rightHUD =driver.findElement(By.id("rightHUD"));
		WebElement rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
		
		 
		try
		{
			WebElement userArea1=rightHUD_yourturn.findElement(By.className("userArea"));
			if(userArea1.findElement(By.className("round_no")).isDisplayed())
			{
				status=2;
				System.out.println("round no found  "+ status +"status");
			}
		}catch(Exception e)
		{
			//else create a new game and verify right HUD carousals
			System.out.println("no game is found hence creating a new game");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// new game creation
			WebElement startButton =driver.findElement(By.id("startButton"));
			startButton.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			WebElement friendlist=driver.findElement(By.className("friendlist"));
			WebElement rep=friendlist.findElement(By.className("rep"));
			WebElement select_button=rep.findElement(By.tagName("a"));
			select_button.click();
			WebElement sendinvite=driver.findElement(By.id("sendinvite"));
			sendinvite.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement okButton = driver.findElement(By.className("msgbox-ok"));
			if (okButton != null) {
				System.out.println("ok button found");
			}
			okButton.click();
			// check Right HUD
			 	rightHUD =driver.findElement(By.id("rightHUD"));
			 	rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
			 	
			 	WebElement userArea2=rightHUD_yourturn.findElement(By.className("userArea"));
				if(userArea2.findElement(By.className("round_no")).isDisplayed())
				{
					status=2;
				}
			
		}
		System.out.print("status "+status);
		assertEquals(2,status);
	}
    @Test
    public void unionMonitorStaging()
    {
    	Properties prop = new Properties();
    	String url=null,userid=null,userpwd=null;
    	try {
               //load a properties file
    		prop.load(new FileInputStream("environment.properties"));
 
               //get the property value and print it out
                 url=prop.getProperty("url");
    		userid=prop.getProperty("userid");
    		userpwd=prop.getProperty("userpwd");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		int status=0;
	//	System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver");
	//	WebDriver driver= new ChromeDriver();
	//	WebDriver driver=new FirefoxDriver();
	//	WebDriver driver=new OperaDriver();
	//	driver.get(url);
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		WebElement login_form=driver.findElement(By.id("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys(userid);
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys(userpwd);
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//close tutorial in case of db cleanup
		try{
		WebElement popup_wrapper= driver.findElement(By.className("popup-wrapper"));
		WebElement start_popup=popup_wrapper.findElement(By.className("start-popup"));
		WebElement title=start_popup.findElement(By.className("title"));
		String Title=title.getText();
		assertEquals(Title,"Welcome to Zalerio!");
		WebElement left_button=start_popup.findElement(By.className("left-button"));
		left_button.click();
		}catch(Exception e)
		{
			System.out.println("tutorial not found");
		}
		//close pop up
		try{
		WebElement okButton = driver.findElement(By.className("msgbox-ok"));
		if (okButton != null) {
			System.out.println("ok button found");
		}
		okButton.click();
		}catch(Exception e)
		{}
		// close game end popup
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// If Game End Pop up Found then dismiss it
		try{
		WebElement score_friendpopup=driver.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));	
		close.click();
		}catch(Exception e)
		{e.printStackTrace();}
		//close feedback screen
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		try{
			WebElement rating_popup=driver.findElement(By.className("rating-popup"));
			WebElement close1= rating_popup.findElement(By.tagName("a"));
			if(close1.isDisplayed())
			{
				close1.click();
			}
		}catch(Exception e)
		{e.printStackTrace();}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		// verify username is visible
		WebElement leftHUD= driver.findElement(By.id("leftHUD"));
		WebElement gameInfoPanel=leftHUD.findElement(By.className("gameInfoPanel"));
		WebElement gameInfoDiv=gameInfoPanel.findElement(By.className("gameInfoDiv"));
		WebElement userArea=gameInfoDiv.findElement(By.className("userArea"));
		WebElement userAreaName=userArea.findElement(By.id("currentUserName"));
		if(userAreaName.isDisplayed())
		{
			String name=userAreaName.getText();
			if(name.contains("Maria"))
			{
				status=1;
				System.out.println("name is "+name+" status "+status);
			}	
		}
		//verify right HUD game carousals
		
		WebElement rightHUD =driver.findElement(By.id("rightHUD"));
		WebElement rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
		
		 
		try
		{
			WebElement userArea1=rightHUD_yourturn.findElement(By.className("userArea"));
			if(userArea1.findElement(By.className("round_no")).isDisplayed())
			{
				status=2;
				System.out.println("round no found  "+ status +"status");
			}
		}catch(Exception e)
		{
			//else create a new game and verify right HUD carousals
			System.out.println("no game is found hence creating a new game");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// new game creation
			WebElement startButton =driver.findElement(By.id("startButton"));
			startButton.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			WebElement friendlist=driver.findElement(By.className("friendlist"));
			WebElement rep=friendlist.findElement(By.className("rep"));
			WebElement select_button=rep.findElement(By.tagName("a"));
			select_button.click();
			WebElement sendinvite=driver.findElement(By.id("sendinvite"));
			sendinvite.click();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WebElement okButton = driver.findElement(By.className("msgbox-ok"));
			if (okButton != null) {
				System.out.println("ok button found");
			}
			okButton.click();
			// check Right HUD
			 	rightHUD =driver.findElement(By.id("rightHUD"));
			 	rightHUD_yourturn=rightHUD.findElement(By.id("rightHUD-yourturn"));
			 	
			 	WebElement userArea2=rightHUD_yourturn.findElement(By.className("userArea"));
				if(userArea2.findElement(By.className("round_no")).isDisplayed())
				{
					status=2;
				}
			
		}
		System.out.print("status "+status);
		assertEquals(2,status);    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
