package com.zalerio.test;

import java.net.URL;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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
import com.zalerio.config.GameUtil;
import com.zalerio.config.Parallelized;

/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 *
 * This test also includes the <a href="">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
 *
 * @author Ross Rowe
 */
@RunWith(Parallelized.class)
public class ZalerioBaseTest implements SauceOnDemandSessionIdProvider {

	 private String browser;
	    private String browserVersion;
	    private String os;
	    private String userid;
	    private String password;
	//public ZalerioBaseTest()
	//{
		
//	}
	public ZalerioBaseTest(String os, String browser, String version,String userid,String password)  
    {
        super();
        this.browser = browser;
        this.browserVersion = version;
        this.os = os;
        this.userid=userid;
        this.password=password;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Parameters
    public static LinkedList browsersStrings() throws Exception { 
      LinkedList browsers = new LinkedList();
      String OS[]={"Windows 2008","Windows 2008"};//,"Mac 10.6","Windows 2008"};
      String browser[]={"firefox","googlechrome"};//',"safari","opera"};
      String version[]={"13"," "};//,"10","5","11"};
      String userid[]={"abhilashbhaduri@gmail.com","griffinsingh1@gmail.com"};//,"rahulmu550@gmail.com","hemantkumer007@gmail.com","mahasingh50@gmail.com"};
      String password[]={"16081989","griffinsingh1"};//,"rahulmu550","hemantkumer007","mahasingh50"};
      for(int i=0;i<=1;i++)
      {
       browsers.add(new String[] {OS[i], browser[i], version[i], userid[i],password[i] });
      } 
    return browsers;
	}
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

        DesiredCapabilities capabillities=null ;;
        if(browser.contains("firefox"))
        {
        	capabillities = DesiredCapabilities.firefox();
        }
        else if(browser.contains("googlechrome"))
        {
        	capabillities = DesiredCapabilities.chrome();
        }
        else if(browser.contains("safari"))
        {
        	capabillities=DesiredCapabilities.safari();
        }
        else if(browser.contains("ie"))
        {
        	capabillities=DesiredCapabilities.internetExplorer();
        }
        else if(browser.contains("opera"))
        {
        	capabillities=DesiredCapabilities.opera();
        }
        capabillities.setCapability("version", browserVersion);
        capabillities.setCapability("platform", os);
        capabillities.setCapability("name",  testName.getMethodName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        this.sessionId = ((RemoteWebDriver)driver).getSessionId().toString();
    
        driver.get("http://apps.facebook.com/zalzerostaging/");
        WebElement email=driver.findElement(By.id("email"));
		 WebElement pass=driver.findElement(By.id("pass"));
		WebElement login=driver.findElement(By.id("login_form"));
		
		email.sendKeys(userid);
		pass.sendKeys(password);
		login.submit();
		driver.switchTo().frame("iframe_canvas");
		GameUtil.clickPlayHereForMultiTabIssue(driver);
		
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
