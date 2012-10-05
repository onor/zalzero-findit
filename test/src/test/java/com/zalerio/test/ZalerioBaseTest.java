package com.zalerio.test;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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
import com.zalerio.config.Config;

public class ZalerioBaseTest  implements SauceOnDemandSessionIdProvider {
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

    protected WebDriver driver;

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
        //this.driver = new FirefoxDriver();
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
    
    @After
    public void tearDown() throws Exception {
    	driver.quit();
    }
}
