package com.zalerio.test;


import static junit.framework.Assert.assertEquals;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.zalerio.config.Config;

public class GameEnterTest implements SauceOnDemandSessionIdProvider {
	
    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("snehesh_mobicules", "f1d71e1f-2a83-4362-8364-79ea37f41418");

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
        //capabillities.setCapability("version", "15");
        capabillities.setCapability("platform", Platform.VISTA);
        capabillities.setCapability("name",  testName.getMethodName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
        
        // Open the Game
 		// login FB -ZALZEROSTAGING
 		driver.get("http://apps.facebook.com/zalzerostaging/");
 		
 		try {
 			Thread.sleep(5000);
 		} catch (InterruptedException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
 		
 		// Login into Facebook
 		WebElement userEmail = driver.findElement(By.id("email"));
 		WebElement userPassword = driver.findElement(By.id("pass"));
 		userEmail.sendKeys(Config.FB_UNSECURED_ACCOUNT_USERNAME);
 		userPassword.sendKeys(Config.FB_UNSECURED_ACCOUNT_PASSWORD);
 		
 		WebElement login = driver.findElement(By.name("login"));
 		login.submit();
 		
 		// Switching to Zalerio Game Iframe
 		driver.switchTo().frame("iframe_canvas");
 		try {
 			Thread.sleep(10000);
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

	@Test
	public void nonSecuredGameOpenTest() {
		try {
			WebElement playHereButton = driver.findElement(By.className("playhere"));
			if(playHereButton.isDisplayed()) {
				playHereButton.click();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Click Start a New Game
		WebElement startButton = driver.findElement(By.id("startButton"));
		assertEquals(startButton.isDisplayed(),true);
	}
	
	@Test
	public void nonSecuredStartANewGame() {
		// If Game End Pop up Found then dismiss it
		try {
			WebElement dismiss = driver.findElement(By.className("dismiss"));
			if (dismiss.isDisplayed()) {
				dismiss.click();
			}
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Click Start a New Game
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		
		WebElement findFriendsHeader = driver.findElement(By.className("findfriend"));
		assertEquals(findFriendsHeader.isDisplayed(),true);
	}
	
    @After
    public void tearDown() throws Exception {
    	driver.quit();
    }
}