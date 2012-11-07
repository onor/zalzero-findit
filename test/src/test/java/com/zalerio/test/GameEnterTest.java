package com.zalerio.test;


import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.zalerio.config.Config;
import com.zalerio.config.GameUtil;
import com.zalerio.config.UserLogin;

public class GameEnterTest extends ZalerioBaseTest {
	public GameEnterTest(String os, String browser, String version)
	{
		super(os, browser, version);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void nonSecuredGameOpenTest() {
		String emailid=Config.FB_SECURED_ACCOUNT_USERNAME;
		String password=Config.FB_SECURED_ACCOUNT_PASSWORD;
		UserLogin.Olduserlogin(driver1, emailid, password);
		
		
		// Click Start a New Game
		WebElement startButton = driver1.findElement(By.id("startButton"));
		assertEquals(startButton.isDisplayed(),true);
	}
	
	@Test
	public void nonSecuredStartANewGame() {
		String emailid=Config.FB_SECURED_ACCOUNT_USERNAME;
		String password=Config.FB_SECURED_ACCOUNT_PASSWORD;
		UserLogin.Olduserlogin(driver1, emailid, password);
		// Click Start a New Game
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		
		WebElement findFriendsHeader = driver1.findElement(By.className("findfriend"));
		assertEquals(findFriendsHeader.isDisplayed(),true);
	}
	

}