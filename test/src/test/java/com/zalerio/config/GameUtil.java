package com.zalerio.config;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GameUtil {
	
	public static void clickPlayHereForMultiTabIssue(WebDriver driver) {
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
	}
	
	public static void closeGameEndPopUp(WebDriver driver) {
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
	}

}
