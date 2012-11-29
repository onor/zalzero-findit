package com.zalerio.config;


import java.util.List;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GameFeatures {
	public static void createGame(WebDriver driver, int SelectedFriends[]) throws InterruptedException
	{
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		// select n friends
		for(int i=0;i<SelectedFriends.length;i++)
		{	
		WebElement friend = selectListOfButtons.get(SelectedFriends[i]).findElement(By.tagName("a"));
		friend.click();
		}
		//send challenge
		WebElement sendChallengeButton = driver.findElement(By
						.id("sendinvite"));
		sendChallengeButton.click();
		System.out.println("challenge sent");
		Popup.closePopup(driver);
		Thread.sleep(2000);
	}
	public static String grabGameId(WebDriver driver)
	{
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
	public static void acceptInvitation(WebDriver driver, String NewGameId)
	{
		// accept invitation
			WebElement	rightHUD_yourturn = driver.findElement(By.id("rightHUD-yourturn"));
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
	
// add delayed methods
	public static void createGameWithDelay(WebDriver driver, int SelectedFriends[],WebDriver driver2) throws InterruptedException
	{
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		GameUtil.makebusy(driver2);
		// select n friends
		for(int i=0;i<SelectedFriends.length;i++)
		{	
		WebElement friend = selectListOfButtons.get(SelectedFriends[i]).findElement(By.tagName("a"));
		friend.click();
		}
		//send challenge
		WebElement sendChallengeButton = driver.findElement(By
						.id("sendinvite"));
		sendChallengeButton.click();
		System.out.println("challenge sent");
		Popup.closePopup(driver);
		Thread.sleep(2000);
	}
	public static String grabGameIdWithDelay(WebDriver driver,WebDriver driver2)
	{
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
	public static void acceptInvitationWithDelay(WebDriver driver, String NewGameId,WebDriver driver2)
	{
		// accept invitation
			WebElement	rightHUD_yourturn = driver.findElement(By.id("rightHUD-yourturn"));
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
