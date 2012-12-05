package com.zalerio.test;





import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zalerio.config.GameFeatures;
import com.zalerio.config.GameUtil;
import com.zalerio.config.Popup;
import com.zalerio.config.Tiles;

public class Create2PlayerGameTest extends Zalerio2UserBaseTest {

	// public Create2PlayerGameTest()

	// (String os, String browser, String version,
	// String user1id, String user2id, String password) {
	// super(os, browser, version, user1id, user2id, password);
	
	// }

	@Test
	public void playGame() throws InterruptedException {
		// String user1Email = "abhilashbhaduri@gmail.com";
		// String user1Pass = "16081989"; //Config.FB_SECURED_ACCOUNT_PASSWORD;
		// String user2Email =
		// "griffinsingh1@gmail.com";//Config.FB_SECURED_ACCOUNT_USERNAME2;
		// String user2Pass =
		// "griffinsingh1";//Config.FB_SECURED_ACCOUNT_PASSWORD2;
		// WebDriver driver1 = new FirefoxDriver();
		// // login User1
		// driver1.manage().window().s
		// UserLogin.Olduserlogin(driver1, user1Email, user1Pass);
		// RatingScreenTest.closeGameEndPopupWithVerifyRating(driver1);
		System.out.print("Logged in");
		// verify Username
		// VerifyFeatures.verifyUsername(driver1, "Abhi Vads");
		int[] SelectedFriends = new int[] { 2 };
		// create new game at friendPosition
		GameFeatures.createGameWithDelay(driver1, SelectedFriends, driver2);
		 
		// login user2
		// System.setProperty("webdriver.chrome.driver",
		// "C:/Setup_Abhilash/BrowserDrivers/ChromeDriver/chromedriver.exe");
		// WebDriver driver2 = new ChromeDriver();
		// UserLogin.Olduserlogin(driver2, user2Email, user2Pass);
		// grab new GameId
		String NewGameId= GameFeatures.grabGameIdWithDelay(driver1, driver2);
		
		GameFeatures.acceptInvitationWithDelay(driver2, NewGameId,driver1);
		//shift to user 1 to verify tiles drag and drop
		// click Play and verify Error Pop up msg
		WebElement play = driver1.findElement(By.id("placeBetOnServer"));
		play.click();
		// verify pop up and close it
		Popup.verifyPopup(driver1, "No bets placed!");
		// drag and drop tiles
		WebElement gamewall = driver1.findElement(By.id("gamewall"));
		WebElement gameBetPanel = driver1.findElement(By.id("gameBetPanel"));

		// drag and place 1 tile on board and click play to verify pop up
		// drag and drop bets
		WebElement bet_0 = gameBetPanel.findElement(By.id("bet_0"));
		WebElement pos = gamewall.findElement(By.id("boardTile-483"));
		Tiles.drag1Tile(driver1, bet_0, pos);
		// click play to verify popup
		play.click();
		GameUtil.makebusy(driver2);
		Popup.verifyPopup(driver1,
				"Not so fast... please place all of your 9 tiles!");
		// place 1 tile outside board
		 WebElement startButton = driver1.findElement(By.id("startButton"));
		Thread.sleep(1000);
		WebElement bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, startButton);
		// place 1 tile on same position as tile0
		Tiles.drag1Tile(driver1, bet_1, pos);
		// interchange tile positions in tray
		bet_0 = gameBetPanel.findElement(By.id("bet_0"));
		bet_1 = gameBetPanel.findElement(By.id("bet_1"));
		Tiles.drag1Tile(driver1, bet_1, bet_0);
		GameUtil.makebusy(driver2);
		// drag rest of tiles at random and place on board.
		for (int i = 1; i < 9; i++) {
			String betid = "bet_" + i;
			String betPos = "boardTile-" + i;
			WebElement bet = gameBetPanel.findElement(By.id(betid));
			WebElement position = gamewall.findElement(By.id(betPos));
			Tiles.drag1Tile(driver1, bet, position);
			Thread.sleep(1000);
			GameUtil.makebusy(driver2);
		}
		
		// interchange position of tiles on board
		WebElement tilePresentPosition = gamewall.findElement(By
				.id("boardTile-5"));
		WebElement tileNextPosition = gamewall.findElement(By
				.id("boardTile-482"));
		Tiles.drag1Tile(driver1, tilePresentPosition, tileNextPosition);
		// drag tile from board to tray
		tilePresentPosition = gamewall.findElement(By.id("boardTile-482"));
		tileNextPosition = gameBetPanel.findElement(By.id("bet_3"));
		Tiles.drag1Tile(driver1, tilePresentPosition, tileNextPosition);
		GameUtil.makebusy(driver2);
		// click Play
		play.click();
		// change position of tiles on board TODO unsuccessful need to be redone
				// tilePresentPosition = gamewall.findElement(By.id("boardTile-90"));
				// String draggable = tilePresentPosition.getAttribute("draggable");
				// click Play to verify popup
				play.click();
				Popup.verifyPopup(driver1,
						"Not so fast.... lets wait for your friends to play their turn");
						
				GameUtil.makebusy(driver2);
		Thread.sleep(2000);
		//check game screen by user 2
		// check Cheat Sheet,Help,Stats,game tile,More
				// cheat sheet
				WebElement cheatSheetDiv = driver2.findElement(By
						.className("friendChallenge"));
				cheatSheetDiv.click();
				Thread.sleep(2000);
				cheatSheetDiv.click();
				
				// Help
				WebElement helpButton = driver2.findElement(By.className("helpButton"));
				helpButton.click();
				GameUtil.makebusy(driver1);
				WebElement s_friendpopup = driver2.findElement(By.id("s_friendpopup"));
				WebElement close = s_friendpopup.findElement(By.id("close"));
				close.click();
				// Stats
				WebElement stats = driver2.findElement(By.id("bottomHUDbuttons-stats"));
				stats.click();
				Thread.sleep(2000);
				WebElement s_friendpopup1 = driver2.findElement(By.id("s_friendpopup"));
				WebElement close1 = s_friendpopup1.findElement(By.id("close"));
				close1.click();
				GameUtil.makebusy(driver1);
				//User 2 places all round 1 tiles and hits play
				// drag and drop all tiles
				Tiles.dragAllTilesWithDelay(driver2, driver1);
				WebElement play2 = driver2.findElement(By.id("placeBetOnServer"));
				play2.click();
				
		
		// shift to user 1 to verify round end
						boolean status;
		Thread.sleep(3000);
		// check ScoreBoard
		WebElement leftHUD = driver1.findElement(By.id("leftHUD"));
		WebElement gameScore = leftHUD.findElement(By.className("gameScore"));
		WebElement gameScore_round = gameScore.findElement(By
				.className("gameScore-round"));
		WebElement doneRound = gameScore_round.findElement(By
				.className("doneRound"));
		String roundNo = doneRound.getText();
		status = roundNo.contains("1");
		assertEquals(status, true);
		GameUtil.makebusy(driver2);
		WebElement currentRound = gameScore_round.findElement(By
				.className("currentRound"));
		roundNo = currentRound.getText();
		status = roundNo.contains("2");
		assertEquals(status, true);
		WebElement notPlayedRound = gameScore_round.findElement(By
				.className("notPlayedRound"));
		roundNo = notPlayedRound.getText();
		status = roundNo.contains("3");
		assertEquals(status, true);
		WebElement gameScore_players = gameScore.findElement(By
				.className("gameScore-players"));
		WebElement userScoreHUDMain = gameScore_players.findElement(By
				.id("userScoreHUDMain"));
		List<WebElement> userScoreHUD_player = userScoreHUDMain.findElements(By
				.className("userScoreHUD_player"));
		GameUtil.makebusy(driver2);
		// check player1
		WebElement player1 = userScoreHUD_player.get(0);
		WebElement userScoreHUD_playerBetPlacedNoarrow = player1.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		status = userScoreHUD_playerBetPlacedNoarrow.isDisplayed();
		assertEquals(status, true);
		WebElement userScoreHUD_playerSerial = player1.findElement(By
				.className("userScoreHUD_playerSerial"));
		status = userScoreHUD_playerSerial.getText().contains("1");
		assertEquals(status, true);
		WebElement userScoreHUD_playerName = player1.findElement(By
				.className("userScoreHUD_playerName"));
		status = userScoreHUD_playerName.isDisplayed();
		assertEquals(status, true);
		
		WebElement userScoreHUD_playerScore = player1.findElement(By
				.className("userScoreHUD_playerScore"));
		status = userScoreHUD_playerScore.isDisplayed();
		assertEquals(status, true);
		GameUtil.makebusy(driver2);
		// check player2
		WebElement player2 = userScoreHUD_player.get(1);
		userScoreHUD_playerBetPlacedNoarrow = player2.findElement(By
				.className("userScoreHUD_playerBetPlacedNo"));
		status = userScoreHUD_playerBetPlacedNoarrow.isDisplayed();
		assertEquals(status, true);
		userScoreHUD_playerSerial = player2.findElement(By
				.className("userScoreHUD_playerSerial"));
		status = userScoreHUD_playerSerial.getText().contains("2");
		assertEquals(status, true);
		userScoreHUD_playerName = player2.findElement(By
				.className("userScoreHUD_playerName"));
		status = userScoreHUD_playerName.isDisplayed();
		assertEquals(status, true);
		GameUtil.makebusy(driver2);
		userScoreHUD_playerScore = player2.findElement(By
				.className("userScoreHUD_playerScore"));
		status = userScoreHUD_playerScore.isDisplayed();
		assertEquals(status, true);
		// check gameTile
		WebElement rightHUD_yourturn = driver1.findElement(By
				.id("rightHUD-yourturn"));
		WebElement gameTile = rightHUD_yourturn.findElement(By.id(NewGameId));
		status = gameTile.findElement(By.className("round_no")).getText()
				.contains("2");
		assertEquals(status, true);
		GameUtil.makebusy(driver2);
		// Play User1 round2
		Tiles.dragAllTilesWithDelay(driver1, driver2);
		play.click();
		// Play User2 round2
		Tiles.dragAllTilesWithDelay(driver2, driver1);
		play2.click();
		// Play User1 round3
		Tiles.dragAllTilesWithDelay(driver1, driver2);
		 play.click();
		// Play User2 round3
		 Tiles.dragAllTilesWithDelay(driver2, driver1);
		 play2.click();
		// Play User1 round4
		 Tiles.dragAllTilesWithDelay(driver1, driver2);
		 play.click();
		// Play User2 round4
		 Tiles.dragAllTilesWithDelay(driver2, driver1);
		 play2.click();
		// Play User1 round5
		 Tiles.dragAllTilesWithDelay(driver1, driver2);
		 play.click();
		// Play User2 round5
		 Tiles.dragAllTilesWithDelay(driver2, driver1);
		 play2.click();

		// Play User1 round6
		 Tiles.dragAllTilesWithDelay(driver1, driver2);
		 play.click();
		// Play User2 round6
		 Tiles.dragAllTilesWithDelay(driver2, driver1);
		play2.click();
		// Play User1 round7
		Tiles.dragAllTilesWithDelay(driver1, driver2);
		 play.click();
		// Play User2 round7
		 Tiles.dragAllTilesWithDelay(driver2, driver1);
		 play2.click();
		Thread.sleep(2000);
		// verify rating screen pop up
		// RatingScreenTest.closeGameEndPopupWithVerifyRating(driver1);
		// handle finish game pop up
		WebElement score_friendpopup = driver2.findElement(By
				.id("score_friendpopup"));
		// click dismiss
		WebElement show_score = score_friendpopup.findElement(By
				.className("show_score"));
		WebElement button_score = show_score.findElement(By
				.className("button_score"));
		List<WebElement> buttons = button_score.findElements(By.tagName("a"));
		WebElement dismiss = buttons.get(1);
		dismiss.click();
		GameUtil.closeGameEndPopUp(driver1);
		// click x of rating screen
		WebElement rating_popup = driver2.findElement(By
				.className("rating-popup"));
		close1 = rating_popup.findElement(By.tagName("a"));
		close1.click();
		
	}
}
