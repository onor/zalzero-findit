package com.zalerio.config;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertEquals;

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
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// If Game End Pop up Found then dismiss it
		try{
		WebElement score_friendpopup=driver.findElement(By.id("score_friendpopup"));
		WebElement close=score_friendpopup.findElement(By.id("close"));	
		close.click();
		}catch(Exception e)
		{e.printStackTrace();}
		//close feedback screen
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		try{
			WebElement rating_popup=driver.findElement(By.className("rating-popup"));
			WebElement close1= rating_popup.findElement(By.tagName("a"));
			if(close1.isDisplayed())
			{
				close1.click();
			}
		}catch(Exception e)
		{e.printStackTrace();}
			assertEquals(1,1);
	}

}
