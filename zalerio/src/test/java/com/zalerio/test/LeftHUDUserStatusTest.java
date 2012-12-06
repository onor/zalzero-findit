package com.zalerio.test;




import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Stats;
import com.zalerio.config.Tiles;

public class LeftHUDUserStatusTest extends Zalerio2UserBaseTest {
	@Test
	public void checkStatus() throws InterruptedException {
		
	/*	WebDriver driver1 = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver",
				"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		WebDriver driver2 = new ChromeDriver();
		
		String user1id = Config.FB_SECURED_ACCOUNT_USERNAME;
		String user1pass = Config.FB_SECURED_ACCOUNT_PASSWORD;
		String user2id = "hemantkumer007@gmail.com";
		String user2pass = "hemantkumer007";
		UserLogin.Olduserlogin(driver1, user1id, user1pass);
		UserLogin.Olduserlogin(driver2, user2id, user2pass);
		*/
		// user1 creates a new game
		int SelectedFriends[]=new int[]{2};
		GameFeatures.createGameWithDelay(driver1, SelectedFriends, driver2);
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
		GameUtil.makebusy(driver2);
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
		GameUtil.makebusy(driver2);
		// grab new GameId
	String NewGameId=GameFeatures.grabGameIdWithDelay(driver1,driver2);
		//accept invitation by user2
	
		GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
		GameUtil.makebusy(driver2);
		Thread.sleep(2000);
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
				GameUtil.makebusy(driver2);
				WebElement userinfo2 = infoPlate2.findElement(By.className("userinfo"));
				assertEquals(userinfo2.findElement(By.className("username"))
						.isDisplayed(), true);
				WebElement userPlayStatus2 = userinfo2.findElement(By
						.className("userPlayStatus"));
				GameUtil.makebusy(driver2);
				String userStatus2 = userPlayStatus2.getText();
				String color2=userPlayStatus2.getAttribute("class");
				assertEquals(color2.contains("green"),true);
				assertEquals(userStatus2, "...playing now");
		//drag and drop all tiles of user2 and click play
				Tiles.dragAllTilesWithDelay(driver2, driver1);
				WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
				play2.click();
		//make user2 leave game screen
				driver2.switchTo().defaultContent();
			driver2.findElement(By.id("pageLogo")).click();		
		//check user2 status as "finished round"
			GameUtil.makebusy(driver2);
				 gameInfo_game_players = driver1.findElement(By
						.id("gameInfo-game-players"));
				 background = gameInfo_game_players.findElement(By
						.className("background"));
				 infoPlate = background
						.findElement(By.className("infoPlate"));
				GameUtil.closeGameEndPopUp(driver2);
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
				GameUtil.makebusy(driver2);
		//drag and drop all tiles by user1
				Tiles.dragAllTilesWithDelay(driver1, driver2);
				WebElement play1 = driver1.findElement(By.id("placeBetOnServer"));
				play1.click();
				Thread.sleep(8000);
				GameUtil.makebusy(driver2);
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
				GameUtil.makebusy(driver2);
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
					GameUtil.makebusy(driver2);
					WebElement resignme =driver1.findElement(By.id("resignme"));
					resignme.click();
					GameUtil.makebusy(driver2);
					// RatingScreenTest.closeGameEndPopupWithVerifyRating(driver1,driver2);
					// check stats
					 Stats.verifyGameAddToPastGames(driver1, NewGameId);
	
	}

}
