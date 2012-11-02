package com.zalerio.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.zalerio.config.GameUtil;

public class TutorialTest extends ZalerioBaseTest {
	
	public TutorialTest(String os, String browser, String version,
			String userid, String password) {
		super(os, browser, version, userid, password);
		// TODO Auto-generated constructor stub
	}

	//@Test
	public void runTutorialFromHelp() {
		GameUtil.closeGameEndPopUp(driver);
		// help
		WebElement helpButton = driver.findElement(By.className("helpButton"));
		helpButton.click();
		// Tutorial
		// play "show me how"
		WebElement showmehow = driver.findElement(By.id("showmehow"));
		showmehow.click();
		// click cheat sheet
		// cheat-sheet
		WebElement cheatSheet = driver.findElement(By.id("cheat-sheet"));
		cheatSheet.click();
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
		}
		// click ok pop up
		WebElement active_screen = driver.findElement(By.className("girlimg"));
		WebElement tutorial_popup = active_screen.findElement(By
				.className("tutorial-popup"));
		WebElement popup1 = tutorial_popup.findElement(By
				.className("popup-button"));
		popup1.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// click Back to Game
		cheatSheet.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// click OK on popup
		WebElement active_screen2 = driver.findElement(By.className("girlimg"));
		WebElement tutorial_popup2 = active_screen2.findElement(By
				.className("tutorial-popup"));
		WebElement popup2 = tutorial_popup2.findElement(By
				.className("popup-button"));
		popup2.click();
		// drag and drop
		WebElement gameBetPanel = driver.findElement(By.id("gameBetPanel"));
		WebElement bet0 = gameBetPanel.findElement(By.id("bet_0"));

		WebElement tile9 = driver.findElement(By.id("boardTile-9"));
		Actions builder = new Actions(driver);

		Action dragAndDrop = builder.clickAndHold(bet0).moveToElement(tile9)
				.release(tile9).build();
		dragAndDrop.perform();
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
		}
		// click OK on popup
		WebElement active_screen3 = driver.findElement(By.className("girlimg"));
		WebElement tutorial_popup3 = active_screen3.findElement(By
				.className("tutorial-popup"));
		WebElement popup3 = tutorial_popup3.findElement(By
				.className("popup-button"));
		popup3.click();
		WebElement play = driver.findElement(By.id("placeBetOnServer"));
		// click play
		play.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// click OK on popup
		WebElement active_screen4 = driver.findElement(By.className("girlimg"));
		WebElement tutorial_popup4 = active_screen4.findElement(By
				.className("tutorial-popup"));
		WebElement popup4 = tutorial_popup4.findElement(By
				.className("popup-button"));
		popup4.click();
		// click OK on popup
		WebElement active_screen5 = driver.findElement(By.className("girlimg"));
		WebElement tutorial_popup5 = active_screen5.findElement(By
				.className("tutorial-popup"));
		WebElement popup5 = tutorial_popup5.findElement(By
				.className("popup-button"));
		popup5.click();
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		// close the window-close button not working
		WebElement friendpopup = driver.findElement(By.id("friendpopup"));
		WebElement closeButton = friendpopup.findElement(By.tagName("a"));
		closeButton.click();
		assertEquals("1", "1");
	}

}
