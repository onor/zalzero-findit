package com.zalerio.test;


import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class GameEnterTest extends ZalerioBaseTest {

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
	

}