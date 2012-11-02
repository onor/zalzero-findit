package com.zalerio.test;


import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameUtil;

public class GameEnterTest extends ZalerioBaseTest {
	public GameEnterTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	//@Test
	public void nonSecuredGameOpenTest() {
		GameUtil.closeGameEndPopUp(driver);
		
		
		// Click Start a New Game
		WebElement startButton = driver.findElement(By.id("startButton"));
		assertEquals(startButton.isDisplayed(),true);
	}
	
//	@Test
	public void nonSecuredStartANewGame() {
		GameUtil.closeGameEndPopUp(driver);
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
	

}