package com.zalerio.test;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.VerifyFeatures;

public class NewUserEntersZalerioTest {
	//new user entering Zalerio for 1st time
	@Test
	public void joinNewUserTest()
	{//	WebDriver driver= new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		WebElement login_form=driver.findElement(By.id("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys("pieqtfw_goldmanmanskyescuwitzsonsenbergstein_1350910207@tfbnw.net");
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys("zalerio");
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		//class =popup-wrapper
		WebElement popup_wrapper= driver.findElement(By.className("popup-wrapper"));
		WebElement start_popup=popup_wrapper.findElement(By.className("start-popup"));
		WebElement title=start_popup.findElement(By.className("title"));
		String Title=title.getText();
		assertEquals(Title,"Welcome to Zalerio!");
		WebElement left_button=start_popup.findElement(By.className("left-button"));
		left_button.click();
		VerifyFeatures.verifyIngameScreenFirstTimeEnter(driver);
	}
	// user who has joined fb before but did not play any is same as above so no need to do this now
	@Test
	public void newUserWithNoActiveGames()
	{
		WebDriver driver= new FirefoxDriver();
		driver.get("http://apps.facebook.com/zalzerostaging/?force=play");
		WebElement login_form=driver.findElement(By.id("login_form"));
		WebElement email=driver.findElement(By.id("email"));
		email.sendKeys("pieqtfw_goldmanmanskyescuwitzsonsenbergstein_1350910207@tfbnw.net");
		WebElement pass=driver.findElement(By.id("pass"));
		pass.sendKeys("zalerio");
		login_form.submit();
		driver.switchTo().frame("iframe_canvas");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		//class =popup-wrapper
		WebElement popup_wrapper= driver.findElement(By.className("popup-wrapper"));
		WebElement start_popup=popup_wrapper.findElement(By.className("start-popup"));
		WebElement title=start_popup.findElement(By.className("title"));
		String Title=title.getText();
		assertEquals(Title,"Welcome to Zalerio!");
		WebElement left_button=start_popup.findElement(By.className("left-button"));
		left_button.click();
		VerifyFeatures.verifyIngameScreenFirstTimeEnter(driver);
	}
}
