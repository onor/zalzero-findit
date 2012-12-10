package com.zalerio.config;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Popup {
	public static void closePopup(WebDriver driver) {
		try {
			Thread.sleep(5000);
	//		WebElement popup = driver.findElement(By.className("msgbox-msg"));
			WebElement ok = driver.findElement(By.className("msgbox-ok"));
			ok.click();
		}catch(Exception e){e.printStackTrace();}
	}
	public static void verifyPopup(WebDriver driver,String msg)
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement popup = driver.findElement(By.className("msgbox-msg"));
		assertEquals(popup.isDisplayed(),true);
		String observerdMsg=popup.getText();
		assertEquals(msg, observerdMsg);
		WebElement ok = driver.findElement(By.className("msgbox-ok"));
		ok.click();
	}
}
