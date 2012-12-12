package com.saucelabs.ZalerioTest.test;




import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.ZalerioTest.config.UserLogin;
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
//@RunWith(Parallelized.class)
public class Zalerio2UserBaseTest implements SauceOnDemandSessionIdProvider {
/*
	 private String browser;
	    private String browserVersion;
	    private String os;
	    private String user1id;
	    private String user2id;
	    private String password;
	  
	public ZalerioBaseTest(String os, String browser, String version,String user1id,String user2id,String password)
	{
		 super();
	        this.browser = browser;
	        this.browserVersion = version;
	        this.os = os;
	        this.user1id=user1id;
	        this.user2id=user2id;
	        this.password=password;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Parameters
    public static LinkedList browsersStrings() throws Exception { 
      LinkedList browsers = new LinkedList();
      String OS[]={"Windows 2008","Windows 2008"};//,"Mac 10.6","Windows 2008"};
      String browser[]={"firefox","googlechrome"};//',"safari","opera"};
      String version[]={"13"," "};//,"10","5","11"};
      String userid1[]={"jcxjhdb_narayanansteinsonsenskymanwitzescuberg_1352447048@tfbnw.net","txpbthh_changman_1352447042@tfbnw.net"};
      String userid2[]={"wvojyqz_zuckerwitz_1352447036@tfbnw.net","fekznht_mcdonaldsky_1352447029@tfbnw.net"};
      String password="zalerio";
  //    String userid[]={"abhilashbhaduri@gmail.com","griffinsingh1@gmail.com"};//,"rahulmu550@gmail.com","hemantkumer007@gmail.com","mahasingh50@gmail.com"};
  //    String password[]={"16081989","griffinsingh1"};//,"rahulmu550","hemantkumer007","mahasingh50"};
      for(int i=0;i<=1;i++)
      {
       browsers.add(new String[] {OS[i], browser[i], version[i],userid1[i],userid2[i],password});
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

    protected  RemoteWebDriver driver1;
    protected RemoteWebDriver driver2;

    private String sessionId;

    @Before
    public void setUp() throws Exception {
    	/*
        
        DesiredCapabilities capabilities2=null;
        DesiredCapabilities capabilities1=null;
        
        if(browser.contains("firefox"))
        {
        	capabilities1=capabilities2 = DesiredCapabilities.firefox();
        }
        else if(browser.contains("googlechrome"))
        {
        	capabilities1=capabilities2 = DesiredCapabilities.chrome();
        }
        else if(browser.contains("safari"))
        {
        	capabilities1=capabilities2=DesiredCapabilities.safari();
        }
        else if(browser.contains("ie"))
        {
        	capabilities1=capabilities2=DesiredCapabilities.internetExplorer();
        }
        else if(browser.contains("opera"))
        {
        	capabilities1=capabilities2=DesiredCapabilities.opera();
        }
        capabilities1.setCapability("version", browserVersion);
        capabilities2.setCapability("version", browserVersion);
        capabilities1.setCapability("platform", os);
        capabilities2.setCapability("platform", os);
        capabilities1.setCapability("name",  testName.getMethodName());
        capabilities2.setCapability("name",  testName.getMethodName());
        
        this.driver1 = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities1);
        this.sessionId = ((RemoteWebDriver)driver1).getSessionId().toString();
    
        this.driver2 = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities2);
        this.sessionId = ((RemoteWebDriver)driver2).getSessionId().toString();
        UserLogin.Olduserlogin(driver1, user1id, password);
        UserLogin.Olduserlogin(driver2, user2id, password);
  */
    	String url,user1id,user2id,password;
		//get data from testing.properties
		Properties values= new Properties();
		values.load(new FileInputStream("testing.properties"));
		String type =System.getenv("JOB_NAME");
		if(type.contains("Zalerio-Staging-Test"))
		{
		url=values.getProperty("Staging_url");
		user1id=values.getProperty("user1_stag_username");
		user2id=values.getProperty("user2_stag_username");
		password=values.getProperty("password");
		}	
		else
		{
			url=values.getProperty("Production_url");
			user1id=values.getProperty("user1_stag_username");
			user2id=values.getProperty("user2_stag_username");
			password=values.getProperty("password");
		}	
    	 DesiredCapabilities capabilities2=null;
         DesiredCapabilities capabilities1=null;
         capabilities1=DesiredCapabilities.chrome();
         capabilities2=DesiredCapabilities.chrome();
         capabilities1.setCapability("version", "");
         capabilities2.setCapability("version", "");
         capabilities1.setCapability("platform", "Windows 2008");
         capabilities2.setCapability("platform", "Windows 2008");
         capabilities1.setCapability("name",  testName.getMethodName());
         capabilities2.setCapability("name",  testName.getMethodName());
         
         
         
         this.driver1 = new RemoteWebDriver(
                 new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                 capabilities1);
         this.sessionId = ((RemoteWebDriver)driver1).getSessionId().toString();
     
         this.driver2 = new RemoteWebDriver(
                 new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                 capabilities2);
         this.sessionId = ((RemoteWebDriver)driver2).getSessionId().toString();
         UserLogin.Olduserlogin(driver1,user1id,password,url);
         UserLogin.Olduserlogin(driver2,user2id ,password,url);
    
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
 
    

    @After
    public void tearDown() throws Exception {
    	//logout user
    	driver1.manage().window().maximize();
    	driver2.manage().window().maximize();
    	UserLogin.logout(driver1);
    	UserLogin.logout(driver2);
    	
        driver1.quit();driver2.quit();
    }

}
