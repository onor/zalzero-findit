package com.saucelabs.quickstart_webdriver_junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 * *
 * @author Ross Rowe
 */
public class WebDriverTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "5");
        capabillities.setCapability("platform", Platform.XP);
        this.driver = new RemoteWebDriver(
                new URL("http://cloudbees_zalzero:15b88203-7a0f-4339-804a-fffb6abb2e3c@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
    }

    @Test
    public void webDriver() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
