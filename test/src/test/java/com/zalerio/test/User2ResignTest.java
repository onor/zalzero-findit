package com.zalerio.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.StartAGame;
import com.zalerio.config.Tiles;
import com.zalerio.config.UserLogin;

public class User2ResignTest {
	

		// user2 resigns from a game after user2 accepts game
		// @Test
		public void resignImmediateAfterAccept() throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

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
			// click resign by user2
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
			WebElement winnerscreen = driver2.findElement(By.id("winnerscreen"));
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
			assertEquals(score.getText(), "0");
			GameUtil.closeGameEndPopUp(driver2);
			// TODO verfiy scoreboard

		}

		 //user2 resigns from a game after user2 accepts and user1 drags  all tiles
		// @Test
		public void resignAfterDragFewTiles() throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

			// grab shown name of user
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
			// User1 draga all tiles
			Tiles.dragAllTiles(driver1);
			// click resign by user1
			WebElement bottomHUDbuttons = driver2.findElement(By
					.className("bottomHUDbuttons"));
			WebElement bottomHUDbuttons_more = bottomHUDbuttons.findElement(By
					.id("bottomHUDbuttons-more"));
			bottomHUDbuttons_more.click();
			Thread.sleep(2000);
			boolean status = driver1.findElement(By.className("resignPopup"))
					.isDisplayed();
			assertEquals(status, true);
			status = driver1.findElement(By.id("resignmeClose")).isDisplayed();
			assertEquals(status, true);
			WebElement resignButton = driver1.findElement(By.id("resignme"));
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
			// check observed name of user1 with shown name
			assertEquals(shownName, observedName);
			WebElement score = score1.findElement(By.className("score"));
			assertEquals(score.getText(), "0");
		}

		//user2 resigns from game after pl2 drags all tiles
		// @Test
		public void resignAfterPl2dragsAllTiles() throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

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
			Tiles.dragAllTiles(driver2);
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
			assertEquals(score.getText(), "0");
		}

		// user2 resigns after playing round 1 after 
		// @Test
		public void resignAfterPl1clicksPlay() throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

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
			Tiles.dragAllTiles(driver2);
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
			assertEquals(score.getText(), "0");
		}
		
		//resign with equal scores

		// @Test
		public void resignRoundNWithEqualScores() throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

			// grab shown name of user
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
			// User1 places all tiles sequentially and clicks play
			WebElement gamewall = driver1.findElement(By.id("gamewall"));
			WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));
			for (int i = 0; i < 9; i++) {
				String betid = "bet_" + i;
				String betPos = "boardTile-" + i;
				WebElement bet = gameBetPanel.findElement(By.id(betid));
				WebElement position = gamewall.findElement(By.id(betPos));
				Tiles.drag1Tile(driver1, bet, position);
				Thread.sleep(1000);
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
				Thread.sleep(1000);
			}
			WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			System.out.print("user1 has clicked Play");
			// click resign by user2
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
			int j = 0;
			status = score.getText() != "";
			assertEquals(status, true);
		}
//resign after user1 hits final round 
		@Test
		public void resignFinalRoundAfterUser1HitsPlay()
				throws InterruptedException {
			WebDriver driver1 = new FirefoxDriver();
			String emailid1 = "abhilashbhaduri@gmail.com";
			String password1 = "16081989";
			UserLogin.Olduserlogin(driver1, emailid1, password1);
			int[] SelectedFriends = new int[] { 2 };
			StartAGame.createGame(driver1, SelectedFriends);
			Thread.sleep(2000);
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
			Thread.sleep(2000);
			System.setProperty("webdriver.chrome.driver",
					"C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
			WebDriver driver2 = new ChromeDriver();
			String emailid2 = "griffinsingh1@gmail.com";
			String password2 = "griffinsingh1";
			UserLogin.Olduserlogin(driver2, emailid2, password2);
			Thread.sleep(2000);
			// make user2 accept game
			rightHUD_yourturn = driver2.findElement(By.id("rightHUD-yourturn"));
			WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
			WebElement accept_decline = gameTile.findElement(By
					.className("accept_decline"));
			WebElement accept = accept_decline.findElement(By
					.className("right_hud_accept"));
			accept.click();
			Popup.closePopup(driver2);

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
			Tiles.dragAllTiles(driver1);
			WebElement play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// User2 drags all tiles
			Tiles.dragAllTiles(driver2);
			WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			System.out.print("user1 has clicked Play");
			// user1 places all tile in round 2
			Tiles.dragAllTiles(driver1);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 2
			Tiles.dragAllTiles(driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 3
			Tiles.dragAllTiles(driver1);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 3
			Tiles.dragAllTiles(driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 4
			Tiles.dragAllTiles(driver1);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 4
			Tiles.dragAllTiles(driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 5
			Tiles.dragAllTiles(driver1);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 5
			Tiles.dragAllTiles(driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tile in round 6
			Tiles.dragAllTiles(driver1);
			play = driver1.findElement(By.id("placeBetOnServer"));
			play.click();
			// user2 places all tiles in round 6
			Tiles.dragAllTiles(driver2);
			play2 = driver2.findElement(By.id("placeBetOnServer"));
			play2.click();
			// user1 places all tiles in round 7
			Tiles.dragAllTiles(driver1);
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
			int j = 0;
			status = score.getText() != "";
			assertEquals(status, true);
		}


}
