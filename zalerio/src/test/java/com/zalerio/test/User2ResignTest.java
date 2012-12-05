package com.zalerio.test;




import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Tiles;

public class User2ResignTest extends Zalerio2UserBaseTest {
		// user2 resigns from a game after user2 accepts game
		 @Test
		public void resignImmediateAfterAccept() throws InterruptedException {
		/*	WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
		*/	int[] SelectedFriends = new int[] { 2 };
		GameFeatures.createGameWithDelay(driver1,SelectedFriends, driver2);
		// grab new GameId
		// grab new GameId
					String NewGameId = GameFeatures.grabGameIdWithDelay(driver1, driver2);
					
			/*System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			*/
			// make user2 accept game
			// accept invitation
					GameFeatures.acceptInvitationWithDelay(driver2, NewGameId, driver1);
			// grab shown name of user1
			WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			GameUtil.makebusy(driver2);
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			// click resign by user2
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			GameUtil.makebusy(driver1);
			Thread.sleep(2000);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(2000);
			// verfiy game end popup
			GameUtil.closeGameEndPopUp(driver1);
			WebElement winnerscreen = driver2.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			GameUtil.closeGameEndPopUp(driver1);
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user2 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			assertEquals(score.getText(), "0");
			GameUtil.closeGameEndPopUp(driver2);
			// TODO verfiy scoreboard

		}

		 //user2 resigns from a game after user2 accepts and user1 drags  all tiles
		 @Test
		public void resignAfterDragFewTiles() throws InterruptedException {
			/*WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);*/
			int[] SelectedFriends = new int[] { 2 };
			GameFeatures.createGameWithDelay(driver1, SelectedFriends, driver2);
			// grab new GameId
			String NewGameId =GameFeatures.grabGameIdWithDelay(driver1, driver2);
			/*System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);*/
			// make user2 accept game
			GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
			// grab shown name of user
			WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			GameUtil.closeGameEndPopUp(driver2);
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			// User1 draga all tiles
			
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			
			// click resign by user2
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			GameUtil.closeGameEndPopUp(driver2);
			Thread.sleep(2000);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			GameUtil.closeGameEndPopUp(driver2);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(2000);
			// verfiy game end popup
			WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			GameUtil.closeGameEndPopUp(driver2);
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user1 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			assertEquals(score.getText(), "0");
		}

		//user2 resigns from game after pl2 drags all tiles
		 @Test
		public void resignAfterPl2dragsAllTiles() throws InterruptedException {
		/*	WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
		*/	int[] SelectedFriends = new int[] { 2 };
			GameFeatures.createGameWithDelay(driver1, SelectedFriends,driver2);
			Thread.sleep(2000);
			// grab new GameId
			String NewGameId = GameFeatures.grabGameId(driver1);
		/*	System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
		*/	// make user2 accept game
			GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
			// grab shown name of user1
			WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			// User2 drags all tiles
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			// click resign by user1
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			GameUtil.closeGameEndPopUp(driver1);
			Thread.sleep(2000);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(2000);
			// verfiy game end popup
			WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			GameUtil.closeGameEndPopUp(driver2);
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user2 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			assertEquals(score.getText(), "0");
		}

		// user2 resigns after playing round 1 after 
		 @Test
		public void resignAfterPl1clicksPlay() throws InterruptedException {
		/*	WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
		*/	int[] SelectedFriends = new int[] { 2 };
			GameFeatures.createGameWithDelay(driver1, SelectedFriends,driver2);
			Thread.sleep(2000);
			// grab new GameId
			String NewGameId = GameFeatures.grabGameId(driver1);
			Thread.sleep(2000);
		/*	System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
		*/	// make user2 accept game
			GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);

			// grab shown name of user
			WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			// User2 drags all tiles
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			// User2 clicks Play
			WebElement play = driver2.findElement(By.id("placeBetOnServer"));
			play.click();
			System.out.print("user1 has clicked Play");
			// click resign by user2
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			Thread.sleep(2000);
			GameUtil.closeGameEndPopUp(driver1);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(4000);
			// verfiy game end popup
			WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			GameUtil.closeGameEndPopUp(driver2);
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user2 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			assertEquals(score.getText(), "0");
		}
		
		//resign with equal scores

		 @Test
		public void resignRoundNWithEqualScores() throws InterruptedException {
		/*	WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
		*/	int[] SelectedFriends = new int[] { 2 };
			GameFeatures.createGameWithDelay(driver1, SelectedFriends,driver2);
			GameUtil.closeGameEndPopUp(driver2);
			Thread.sleep(2000);
			// grab new GameId
			String NewGameId = GameFeatures.grabGameIdWithDelay(driver1,driver2);
			GameUtil.closeGameEndPopUp(driver2);
			Thread.sleep(2000);
		/*	System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
		*/	// make user2 accept game
			GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
			// grab shown name of user
			WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			GameUtil.closeGameEndPopUp(driver2);
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			GameUtil.closeGameEndPopUp(driver2);
			// User1 places all tiles sequentially and clicks play
			WebElement gamewall = driver1.findElement(By.id("gamewall"));
			WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));
			for (int i = 0; i < 9; i++) {
				String betid = "bet_" + i;
				String betPos = "boardTile-" + i;
				WebElement bet = gameBetPanel.findElement(By.id(betid));
				WebElement position = gamewall.findElement(By.id(betPos));
				Tiles.drag1Tile(driver1, bet, position);
				GameUtil.closeGameEndPopUp(driver2);
			}
			
			WebElement play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// User2 drags all tiles
			WebElement gamewall2 = driver2.findElement(By.id("gamewall"));
			WebElement gameBetPanel2 = driver2.findElement(By.id("gameBetPanel"));
			for (int i = 0; i < 9; i++) {
				String betid = "bet_" + i;
				String betPos = "boardTile-" + i;
				WebElement bet = gameBetPanel2.findElement(By.id(betid));
				WebElement position = gamewall2.findElement(By.id(betPos));
				Tiles.drag1Tile(driver2, bet, position);
				GameUtil.closeGameEndPopUp(driver1);
			}
			GameUtil.closeGameEndPopUp(driver1);
			WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			System.out.print("user1 has clicked Play");
			// click resign by user2
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			GameUtil.closeGameEndPopUp(driver1);
			bottomHUDbuttons_more.click();
			Thread.sleep(2000);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(2000);
			
			// verfiy game end popup
			WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			GameUtil.closeGameEndPopUp(driver2);
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user2 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			status = score.getText() != "";
			assertEquals(status, true);
		}
//resign after user1 hits final round 
		@Test
		public void resignFinalRoundAfterUser1HitsPlay()
				throws InterruptedException {
	/*		WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
	*/		int[] SelectedFriends = new int[] { 2 };
			GameFeatures.createGameWithDelay(driver1, SelectedFriends,driver2);
			Thread.sleep(2000);
			// grab new GameId
			String NewGameId = GameFeatures.grabGameIdWithDelay(driver1,driver2);
			Thread.sleep(2000);
	/*		System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
	*/		Thread.sleep(2000);
			// make user2 accept game
			GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
			// grab shown name of user
			WebElement leftHUD = driver2.findElement(By.id("leftHUD"));
			WebElement gameInfoPanel = leftHUD.findElement(By
					.className("gameInfoPanel"));
			WebElement gameInfoDiv = gameInfoPanel.findElement(By
					.className("gameInfoDiv"));
			WebElement userArea = gameInfoDiv.findElement(By.className("userArea"));
			// verify user name
			WebElement currentUserName = userArea.findElement(By
					.id("currentUserName"));
			String shownName = currentUserName.getText();
			Thread.sleep(2000);
			// User1 places all tiles sequentially and clicks play
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			WebElement play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// User2 drags all tiles
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			System.out.print("user1 has clicked Play");
			// user1 places all tile in round 2
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 2
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 3
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 3
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 4
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 4
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 5
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 5
			Tiles.dragAllTilesWithDelay(driver2,driver1);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 6
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 6
			Tiles.dragAllTilesWithDelay(driver1, driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tiles in round 7
			Tiles.dragAllTilesWithDelay(driver1,driver2);
			play2 = driver1.findElement(By.id("placeBetOnServer"));
			play2.click();
			// click resign by user1
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			Thread.sleep(2000);
			boolean status = driver2.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver2.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver2.findElement(By.id("resignme"));
			status = resignButton.isDisplayed();
			resignButton.click();
			Thread.sleep(2000);
			// verfiy game end popup
			WebElement winnerscreen = driver1.findElement(By.id("winnerscreen"));
			WebElement score_friendpopup = winnerscreen.findElement(By
					.id("score_friendpopup"));
			WebElement show_score = score_friendpopup.findElement(By
					.className("show_score"));
			WebElement topScore_div = show_score.findElement(By.id("topScore_div"));
			WebElement score1 = topScore_div.findElement(By.id("score1"));
			// user2 will be shown as winner with score 0
			WebElement name = score1.findElement(By.className("name"));
			String observedName = name.getText();
			// check observed name of user2 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			status = score.getText() != "";
			assertEquals(status, true);
		}


}
