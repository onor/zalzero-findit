package com.saucelabs.ZalerioTest.test;



import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class TutorialTest extends Zalerio1UserBaseTest {

	public TutorialTest(String os, String browser, String version) {
		super(os, browser, version);//, userid, password);
		// TODO Auto-generated constructor stub
	}

@Test
	public void runTutorialFromHelp() {

		// help
		WebElement helpButton = driver1.findElement(By.className("helpButton"));
		helpButton.click();
		// Tutorial
		// play "show me how"
		WebElement showmehow = driver1.findElement(By.id("showmehow"));
		showmehow.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// click "start now"
		// class =popup-wrapper
		WebElement popup_wrapper = driver1.findElement(By
				.className("popup-wrapper"));
		WebElement start_popup = popup_wrapper.findElement(By
				.className("start-popup"));
		WebElement title = start_popup.findElement(By.className("title"));
		String Title = title.getText();
		assertEquals(Title, "Welcome to Zalerio!");
		WebElement right_button = start_popup.findElement(By
				.className("right-button"));
		right_button.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// click cheat sheet
		// cheat-sheet
		WebElement cheatSheet = driver1.findElement(By.id("cheat-sheet"));
		cheatSheet.click();
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
		}
		// click ok pop up
		WebElement active_screen = driver1.findElement(By.className("girlimg"));
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
		WebElement active_screen2 = driver1
				.findElement(By.className("girlimg"));
		WebElement tutorial_popup2 = active_screen2.findElement(By
				.className("tutorial-popup"));
		WebElement popup2 = tutorial_popup2.findElement(By
				.className("popup-button"));
		popup2.click();
		// drag and drop
		WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));
		WebElement bet0 = gameBetPanel.findElement(By.id("bet_0"));

		WebElement tile9 = driver1.findElement(By.id("boardTile-9"));
		Actions builder = new Actions(driver1);

		Action dragAndDrop = builder.clickAndHold(bet0).moveToElement(tile9)
				.release(tile9).build();
		dragAndDrop.perform();
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
		}
		// click OK on popup
		WebElement active_screen3 = driver1
				.findElement(By.className("girlimg"));
		WebElement tutorial_popup3 = active_screen3.findElement(By
				.className("tutorial-popup"));
		WebElement popup3 = tutorial_popup3.findElement(By
				.className("popup-button"));
		popup3.click();
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		// click play
		play.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		// click OK on popup
		WebElement active_screen4 = driver1
				.findElement(By.className("girlimg"));
		WebElement tutorial_popup4 = active_screen4.findElement(By
				.className("tutorial-popup"));
		WebElement popup4 = tutorial_popup4.findElement(By
				.className("popup-button"));
		popup4.click();
	
		WebElement ok_button=driver1.findElement(By.className("ok-button"));
		ok_button.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement startGame=driver1.findElement(By.className("startGame"));
		WebElement startButton=startGame.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement findfriend=driver1.findElement(By.className("findfriend"));
		assertEquals(findfriend.isDisplayed(), true);
	}

}
