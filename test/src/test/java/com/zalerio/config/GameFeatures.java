package com.zalerio.config;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GameFeatures {
	public static void createGame(WebDriver driver, int friendList[]) throws InterruptedException
	{
		WebElement startButton = driver.findElement(By.id("startButton"));
		startButton.click();
		Thread.sleep(3000);
		//select friends
		WebElement a = driver.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		int no_of_friends=friendList.length;
		for(int i=0;i<no_of_friends;i++)
		{	
		WebElement fr = selectListOfButtons.get(i).findElement(
				By.className("select_button"));
		fr.click();
		}
		///click "send challenge"
		WebElement sendChallengeButton = driver.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,0)");
		Thread.sleep(5000);
		Popup.closePopup(driver);
	}
	public static void grabGameId(WebDriver driver)
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

	}
	public static void acceptInvitation(WebDriver driver String Newgam,e)
	{
		// accept invitation
			WebElement	rightHUD_yourturn = driver.findElement(By.id("rightHUD-yourturn"));
				WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
				WebElement accept_decline = gameTile.findElement(By
						.className("accept_decline"));
				WebElement accept = accept_decline.findElement(By
						.className("right_hud_accept"));
				accept.click();
				// click OK on Pop up
				Popup.verifyPopup(driver2, "Would you like to start playing");
				// drag and drop all tiles

	}
	

}
