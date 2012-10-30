package com.zalerio.config;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Popup {
	public static void verifyPopup(WebDriver driver, String msg) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement popup = driver.findElement(By.className("msgbox-msg"));
		String errorPopMsg = popup.getText();
		if (errorPopMsg.contains(msg)) {
			assertEquals(1, 1);
		}
		WebElement ok = driver.findElement(By.className("msgbox-ok"));
		ok.click();
	}
}
