package com.zalerio.test;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.Popup;
import com.zalerio.config.Tiles;

import static org.junit.Assert.assertEquals;
import com.zalerio.config.UserLogin;

public class LeftHUDUserStatusTest {
	@Test
	public void checkStatus() throws InterruptedException {
		int friendPosition = 1;
		WebDriver driver1 = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();
		String user1id = "griffinsingh1@gmail.com";
		String user1pass = "griffinsingh1";
		String user2id = "hemantkumer007@gmail.com";
		String user2pass = "hemantkumer007";
		UserLogin.Olduserlogin(driver1, user1id, user1pass);
		UserLogin.Olduserlogin(driver2, user2id, user2pass);
		// user1 creates a new game
		WebElement startButton = driver1.findElement(By.id("startButton"));
		startButton.click();
		Thread.sleep(6000);

		WebElement a = driver1.findElement(By.className("friendlist"));
		List<WebElement> selectListOfButtons = (a.findElements(By
				.className("rep")));
		WebElement fr1 = selectListOfButtons.get(friendPosition).findElement(
				By.className("select_button"));
		fr1.click();
		System.out.println("fr1 selected");

		// load the game on main screen
		// Click Send Challenge
		WebElement sendChallengeButton = driver1.findElement(By
				.id("sendinvite"));
		sendChallengeButton.click();
		// scroll window to default position
		JavascriptExecutor js = (JavascriptExecutor) driver1;
		js.executeScript("window.scrollTo(0,0)");

		// verify pop up and load the game
		Popup.verifyPopup(driver1,
				"Challenge has been sent. Would you like to start playing.");
		Thread.sleep(2000);
		// user status :not accepted yet in red
		WebElement gameInfo_game_players = driver1.findElement(By
				.id("gameInfo-game-players"));
		WebElement background = gameInfo_game_players.findElement(By
				.className("background"));
		WebElement infoPlate = background
				.findElement(By.className("infoPlate"));
		WebElement reminder = infoPlate.findElement(By.className("reminder"));
		boolean status = reminder.isDisplayed() && reminder.isEnabled();
		assertEquals(status, true);
		assertEquals(infoPlate.findElement(By.className("userAreaImg"))
				.isDisplayed(), true);
		WebElement userinfo = infoPlate.findElement(By.className("userinfo"));
		assertEquals(userinfo.findElement(By.className("username"))
				.isDisplayed(), true);
		WebElement userPlayStatus = userinfo.findElement(By
				.className("userPlayStatus"));
		String userStatus = userPlayStatus.getText();
		String color=userPlayStatus.getAttribute("class");
		assertEquals(color.contains("red"),true);
		assertEquals(userStatus, "not accepted yet");
		// grab new GameId
		String NewGameId = "";
		WebElement rightHUD_yourturn = driver1.findElement(By
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
		//accept invitation by user2
		rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		WebElement accept_decline = gameTile.findElement(By
				.className("accept_decline"));
		WebElement accept = accept_decline.findElement(By
				.className("right_hud_accept"));
		accept.click();
		Popup.closePopup(driver2);
		Thread.sleep(5000);
		// user status :...playing now in green
				WebElement gameInfo_game_players2 = driver1.findElement(By
						.id("gameInfo-game-players"));
				WebElement background2 = gameInfo_game_players2.findElement(By
						.className("background"));
				WebElement infoPlate2 = background2
						.findElement(By.className("infoPlate"));
		//		WebElement reminder2 = infoPlate2.findElement(By.className("reminder"));
		//		boolean status2 = reminder2.isDisplayed() && reminder2.isEnabled();
		//		assertEquals(status, true);
				assertEquals(infoPlate2.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				WebElement userinfo2 = infoPlate2.findElement(By.className("userinfo"));
				assertEquals(userinfo2.findElement(By.className("username"))
						.isDisplayed(), true);
				WebElement userPlayStatus2 = userinfo2.findElement(By
						.className("userPlayStatus"));
				String userStatus2 = userPlayStatus2.getText();
				String color2=userPlayStatus2.getAttribute("class");
				assertEquals(color2.contains("green"),true);
				assertEquals(userStatus2, "...playing now");
		//drag and drop all tiles of user2 and click play
				Tiles.dragAllTiles(driver2);
				WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
				play2.click();
		//make user2 leave game screen
			driver2.switchTo().defaultContent();
			driver2.findElement(By.id("pageLogo")).click();		
		//check user2 status as "finished round"
				 gameInfo_game_players = driver1.findElement(By
						.id("gameInfo-game-players"));
				 background = gameInfo_game_players.findElement(By
						.className("background"));
				 infoPlate = background
						.findElement(By.className("infoPlate"));
				
				assertEquals(infoPlate.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				 userinfo = infoPlate.findElement(By.className("userinfo"));
				assertEquals(userinfo.findElement(By.className("username"))
						.isDisplayed(), true);
				 userPlayStatus = userinfo.findElement(By
						.className("userPlayStatus"));
				 userStatus = userPlayStatus.getText();
				color=userPlayStatus.getAttribute("class");
				assertEquals(color.contains("green"),true);
				assertEquals(userStatus, "finished round");
		
		//drag and drop all tiles by user1
				Tiles.dragAllTiles(driver1);
				WebElement play1 = driver1.findElement(By.id("placeBetOnServer"));
				play1.click();
				Thread.sleep(8000);
		//check user2 status "not played yet"	
			 gameInfo_game_players = driver1.findElement(By
						.id("gameInfo-game-players"));
				 background = gameInfo_game_players.findElement(By
						.className("background"));
				 infoPlate = background
						.findElement(By.className("infoPlate"));
				 reminder = infoPlate.findElement(By.className("reminder"));
				 status = reminder.isDisplayed() && reminder.isEnabled();
				assertEquals(status, true);
				assertEquals(infoPlate.findElement(By.className("userAreaImg"))
						.isDisplayed(), true);
				 userinfo = infoPlate.findElement(By.className("userinfo"));
				assertEquals(userinfo.findElement(By.className("username"))
						.isDisplayed(), true);
				 userPlayStatus = userinfo.findElement(By
							.className("userPlayStatus"));
					 userStatus = userPlayStatus.getText();
					assertEquals(userStatus, "not played yet");
		//click resign by user 1 to end game
					WebElement bottomHUD = driver1.findElement(By.id("bottomHUD"));
					WebElement bottomHUDbuttons_more = bottomHUD.findElement(By
							.id("bottomHUDbuttons-more"));
					bottomHUDbuttons_more.click();
					WebElement resignme =driver1.findElement(By.id("resignme"));
					resignme.click();
					driver1.quit();
					driver2.quit();
					
	}

}
