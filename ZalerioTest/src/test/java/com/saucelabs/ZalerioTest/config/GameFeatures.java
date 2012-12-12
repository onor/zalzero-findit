package com.saucelabs.ZalerioTest.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GameFeatures {
	public static void createGame(WebDriver driver, int SelectedFriends[])
			throws InterruptedException {
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		// select n friends
		for (int i = 0; i < SelectedFriends.length; i++) {
			WebElement friend = selectListOfButtons.get(SelectedFriends[i])
					.findElement(By.tagName("a"));
			friend.click();
		}
		// send challenge
		WebElement sendChallengeButton = driver
				.findElement(By.id("sendinvite"));
		sendChallengeButton.click();
		Thread.sleep(2000);
		// click Send Request on alert popup
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		WebElement dialog_2 = driver
				.findElement(By.className("generic_dialog"));
		WebElement generic_dialog_popup = dialog_2.findElement(By
				.className("generic_dialog_popup"));
		WebElement pop_container = generic_dialog_popup.findElement(By
				.className("pop_container"));
		WebElement pop_content = pop_container
				.findElement(By.id("pop_content"));
		WebElement dialog_buttons = pop_content.findElement(By
				.className("dialog_buttons"));
		WebElement rfloat = dialog_buttons.findElement(By.className("rfloat"));
		List<WebElement> uiButton = rfloat.findElements(By
				.className("uiButton"));
		uiButton.get(0).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement iframe = driver.findElement(By.id("iframe_canvas"));
		driver.switchTo().frame(iframe);

		System.out.println("challenge sent");
		Popup.closePopup(driver);
		Thread.sleep(2000);
	}

	public static String grabGameId(WebDriver driver) {
		// grab new GameId
		String NewGameId = "";
		WebElement rightHUD_yourturn = driver.findElement(By
				.id("rightHUD-yourturn"));
		int newSize;
		// access your turn tiles
		try {
			List<WebElement> your_turnTiles = rightHUD_yourturn.findElements(By
					.className("userArea"));
			System.out.print("got your turn tiles");
			newSize = your_turnTiles.size();
			System.out.println("new size" + newSize);
			WebElement gameTile = null;
			gameTile = your_turnTiles.get(newSize - 1);
			NewGameId = gameTile.getAttribute("id");
			System.out.println("got New your turn tile with Id " + NewGameId);
		} catch (Exception f) {
		}
		return NewGameId;

	}

	public static void acceptInvitation(WebDriver driver, String NewGameId) {
		// accept invitation
		WebElement rightHUD_yourturn = driver.findElement(By
				.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement accept = accept_decline.findElement(By
				.className("right_hud_accept"));
		accept.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// click OK on Pop up
		Popup.verifyPopup(driver, "Would you like to start playing");

	}
//get friend position
	public static int getFriendPosition()
	{
		int friendPosition=0;
		String pos;
		//get data from testing.properties
				Properties values= new Properties();
				try {
					values.load(new FileInputStream("testing.properties"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String type =System.getenv("JOB_NAME");
				if(type.contains("Zalerio-Staging-Test"))
				{
				pos=values.getProperty("user2_stag_position");
				}	
				else
				{
					pos=values.getProperty("user2_prod_position");
				}	
				friendPosition=Integer.parseInt(pos);
				return friendPosition;
	}
	
	
	
	
	// add delayed methods
	public static void createGameWithDelay(WebDriver driver,
			int SelectedFriends[], WebDriver driver2)
			throws InterruptedException {
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		GameUtil.makebusy(driver2);
		// select n friends
		for (int i = 0; i < SelectedFriends.length; i++) {
			WebElement friend = selectListOfButtons.get(SelectedFriends[i])
					.findElement(By.tagName("a"));
			friend.click();
		}
		// send challenge
		WebElement sendChallengeButton = driver
				.findElement(By.id("sendinvite"));
		sendChallengeButton.click();
		Thread.sleep(2000);
		// click Send Request on alert popup
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		WebElement dialog_2 = driver
				.findElement(By.className("generic_dialog"));
		WebElement generic_dialog_popup = dialog_2.findElement(By
				.className("generic_dialog_popup"));
		WebElement pop_container = generic_dialog_popup.findElement(By
				.className("pop_container"));
		WebElement pop_content = pop_container
				.findElement(By.id("pop_content"));
		WebElement dialog_buttons = pop_content.findElement(By
				.className("dialog_buttons"));
		WebElement rfloat = dialog_buttons.findElement(By.className("rfloat"));
		List<WebElement> uiButton = rfloat.findElements(By
				.className("uiButton"));
		uiButton.get(0).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement iframe = driver.findElement(By.id("iframe_canvas"));
		driver.switchTo().frame(iframe);
		System.out.println("challenge sent");
		Popup.closePopup(driver);
		Thread.sleep(2000);
	}

	public static String grabGameIdWithDelay(WebDriver driver, WebDriver driver2) {
		// grab new GameId
		String NewGameId = "";
		WebElement rightHUD_yourturn = driver.findElement(By
				.id("rightHUD-yourturn"));
		int newSize;
		GameUtil.makebusy(driver2);
		// access your turn tiles
		try {
			List<WebElement> your_turnTiles = rightHUD_yourturn.findElements(By
					.className("userArea"));
			System.out.print("got your turn tiles");
			newSize = your_turnTiles.size();
			System.out.println("new size" + newSize);
			WebElement gameTile = null;
			gameTile = your_turnTiles.get(newSize - 1);
			NewGameId = gameTile.getAttribute("id");
			System.out.println("got New your turn tile with Id " + NewGameId);
		} catch (Exception f) {
		}
		return NewGameId;

	}

	public static void acceptInvitationWithDelay(WebDriver driver,
			String NewGameId, WebDriver driver2) {
		// accept invitation
		WebElement rightHUD_yourturn = driver.findElement(By
				.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement accept = accept_decline.findElement(By
				.className("right_hud_accept"));
		accept.click();
		GameUtil.makebusy(driver2);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// click OK on Pop up
		Popup.verifyPopup(driver, "Would you like to start playing");

	}
}
